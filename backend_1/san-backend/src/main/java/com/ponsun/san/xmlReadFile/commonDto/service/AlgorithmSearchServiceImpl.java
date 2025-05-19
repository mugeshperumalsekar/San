package com.ponsun.san.xmlReadFile.commonDto.service;

import com.ponsun.san.algorithm.ScoringCalculatorService;
import com.ponsun.san.dto.RecordDTO;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.search.request.CreateSearchRequest;
import com.ponsun.san.searchLifcycle.search.services.SearchWritePlatformService;
import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSanctionSearchRequest;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlgorithmSearchServiceImpl implements AlgorithmSearchService{

    private final RestTemplate restTemplate;
    private final ScoringCalculatorService scoringCalculatorService;
    private final SearchWritePlatformService searchWritePlatformService;
    @Value("${external.api.base-url}")
    private String apiUrl;
    @Override
    public List<UiSingleParaResponse> calculateAlgorithmSearch(MultiParaSearchData searchData, List<ArabicSanData> countryData,Integer fileType) throws ExecutionException, InterruptedException {
        CreateSearchRequest request = new CreateSearchRequest();
        request.setName(searchData.getName());
        request.setEntryType(searchData.getEntityType());
        request.setMatching_score(Double.valueOf(searchData.getScore()));
        request.setListId(Integer.valueOf(searchData.getId()));
        request.setCountryId(Integer.valueOf(searchData.getCountry()));
        request.setUid(searchData.getUid());

        Response response = this.searchWritePlatformService.createSearch(request);
        Integer searchId = (Integer) response.getId();

        System.out.println("Created search with ID: " + searchId);
        System.out.println("Fetching algorithm records...");

        List<UiSingleParaResponse> result = externalApiCall(searchData, countryData);

        List<RecordDTO> recordDTOList = new ArrayList<>();

        for (UiSingleParaResponse data : result) {
            RecordDTO dto = new RecordDTO();
            dto.setSearchId(searchId);
            dto.setNAME(data.getName());
            dto.setCriminalId(data.getPersion_id());
            dto.setNationality(data.getCountry());
            dto.setFileType(fileType);

            double[] scores = data.getOnsideMultiPara();
            if (scores != null && scores.length > 0) {
                dto.setScore(Arrays.stream(scores).max().orElse(0.0));
            }

            recordDTOList.add(dto);
        }

        System.out.println("recordDTOList" + recordDTOList);
        List<RecordDTO> finalRecords = scoringCalculatorService.processAndSaveRecords(recordDTOList, searchId);

        System.out.println("Final saved records: " + finalRecords.size());
        for (RecordDTO dto : finalRecords) {
            System.out.println(">>> Saved Record: " + dto.getNAME() + " | Score: " + dto.getScore());
        }

        return result;
    }

    @Override
    public List<UiSingleParaResponse> externalApiCall(MultiParaSearchData multiParaSearchData, List<ArabicSanData> filtered) {
        UiSanctionSearchRequest request = new UiSanctionSearchRequest(multiParaSearchData, filtered);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<UiSanctionSearchRequest> entity = new HttpEntity<>(request, headers);
            URI uri = UriComponentsBuilder
                    .fromHttpUrl(apiUrl)
                    .path("/SanctionsSearch")
                    .build()
                    .toUri();

            ResponseEntity<List<UiSingleParaResponse>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<UiSingleParaResponse>>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API call failed", e);
        }
    }
}
