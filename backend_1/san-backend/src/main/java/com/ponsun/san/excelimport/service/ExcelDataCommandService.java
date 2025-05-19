package com.ponsun.san.excelimport.service;

import com.ponsun.san.EhcachePOC.Data.OFACData;
import com.ponsun.san.EhcachePOC.Service.OFACSearchService;
import com.ponsun.san.dto.ScreenDTO;
import com.ponsun.san.dto.SearchDTO;
import com.ponsun.san.excelimport.dto.ExcelDataDto;
import com.ponsun.san.excelimport.entity.ExcelData;
import com.ponsun.san.excelimport.repository.ExcelDataRepository;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.searchDetails.request.CreateSearchDetailsRequest;
import com.ponsun.san.searchLifcycle.searchDetails.services.SearchDetailsWritePlatformService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelDataCommandService {

    private final ExcelDataRepository excelDataRepository;
    private final OFACSearchService ofacsearchService;
    private final ExcelReadService excelReadService;
    private final SearchDetailsWritePlatformService searchDetailsWritePlatformService;

    public ExcelData save(ExcelDataDto input) {
        ExcelData excelData = new ExcelData();
        excelData.setName(input.getName());
        excelData.setType(input.getType());
        excelData.setScore(input.getScore());
        excelData.setCountry(input.getCountry());
        return excelDataRepository.save(excelData);
    }

    @Transactional
    public Response saveBulkData(List<Map<String, Object>> rows) {
        try {
            long totalRecordSaved = 0L;
            long startTime = System.currentTimeMillis();

            List<SearchDTO> searchDTOList = new ArrayList<>();

            for (Map<String, Object> row : rows) {
                try {
                    final String name = getStringValue(row.get("name"));
                    final Object scoreObject = row.get("score");
                    final double parsedScore = getDoubleValue(scoreObject);

                    SearchDTO searchDTO = new SearchDTO();
                    searchDTO.setName(name);
                    searchDTO.setMatching_score(parsedScore);
                    searchDTO.setIsBulkSearch(1);
                    searchDTO.setEntryType(1);
                    searchDTOList.add(searchDTO);

                    totalRecordSaved++;
                } catch (NullPointerException e) {
                }
            }

            List<OFACData> ofacdataList = this.ofacsearchService.fetchAllOFACData();
            this.excelReadService.calculateScore(searchDTOList, ofacdataList);

            long endTime = System.currentTimeMillis();
            System.out.println("Total milliseconds taken: " + (endTime - startTime));

            return new Response(totalRecordSaved);

        } catch (DataIntegrityViolationException e) {
            log.error("Error while saving bulk data: {}", e.getMessage());
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getStringValue(Object value) {
        if (value instanceof String) {
            return ((String) value).trim();
        } else if (value instanceof Double) {
            return String.valueOf(value);
        } else {
            return "";
        }
    }

    private double getDoubleValue(Object value) {
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        } else if (value instanceof Double) {
            return (Double) value;
        } else {
            return 0.0;
        }
    }


    @Transactional
    public Response saveKycScreeningData(List<ScreenDTO> screenDTOList) {
        try {
            long totalRecordSaved = 0L;
            long startTime = System.currentTimeMillis();
            List<OFACData> ofacdataList = this.ofacsearchService.fetchAllOFACData();

            for (ScreenDTO screenDTO : screenDTOList) {
                CreateSearchDetailsRequest createSearchDetailsRequest = new CreateSearchDetailsRequest();
                createSearchDetailsRequest.setName(screenDTO.getName());
                createSearchDetailsRequest.setMatching_score(screenDTO.getSearchingScore());
                createSearchDetailsRequest.setKycId(screenDTO.getKycId());
                createSearchDetailsRequest.setUid(screenDTO.getUid());
                searchDetailsWritePlatformService.createSearchDetails(createSearchDetailsRequest);

                this.excelReadService.calculateScores(screenDTOList, ofacdataList);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Total milli seconds taken " + (endTime - startTime));
            return new Response(totalRecordSaved);
        } catch (DataIntegrityViolationException e) {
            log.error("Error while saveBulkData {}", e.getMessage());
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

//    @Transactional
//    public Response saveBulkData(List<Map<String, Object>> rows) {
//        try {
//            long totalRecordSaved = 0L;
//            long startTime = System.currentTimeMillis();
//
//            List<SearchDTO> searchDTOList = new ArrayList<>();
//
//            for (Map<String, Object> row : rows) {
//                final String name = ((String) row.get("name")).trim();
//                System.out.println(name);
//                String score = ((String) row.get("score"));
//                System.out.println(score);
//                final String type = ((String) row.get("type")).trim();
//                System.out.println(type);
//                final String country = ((String) row.get("country")).trim();
//                System.out.println(country);
//
//                SearchDTO searchDTO = new SearchDTO();
//                searchDTO.setName(name);
//                searchDTO.setMatching_score(Double.parseDouble(score));
//                searchDTO.setIsBulkSearch(1);
//                searchDTO.setEntryType(1);
//                searchDTOList.add(searchDTO);
//                totalRecordSaved = totalRecordSaved + 1;
//            }
//
//            List<OFACData> ofacdataList = this.ofacsearchService.fetchAllOFACData();
//
//            this.excelReadService.calculateScore(searchDTOList, ofacdataList);
//
//            long endTime = System.currentTimeMillis();
//            System.out.println("Total milli seconds taken " + (endTime - startTime));
//            return new Response(totalRecordSaved);
//        } catch (DataIntegrityViolationException e) {
//            log.error("Error while saveBulkData {}", e.getMessage());
//            throw new EQAS_SAN_ApplicationException(e.getMessage());
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }