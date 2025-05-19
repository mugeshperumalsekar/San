package com.ponsun.san.excelimport.service;

import com.ponsun.san.EhcachePOC.Data.OFACData;
import com.ponsun.san.algorithm.ScoringCalculatorService;
import com.ponsun.san.dto.RecordDTO;
import com.ponsun.san.dto.ScreenDTO;
import com.ponsun.san.dto.SearchDTO;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.data.HitRecordData;
import com.ponsun.san.searchLifcycle.hitrecord.rowmapper.HitRecordRowMapper;
import com.ponsun.san.searchLifcycle.hitrecord.services.HitRecordWritePlatformService;
import com.ponsun.san.searchLifcycle.search.request.CreateSearchRequest;
import com.ponsun.san.searchLifcycle.search.services.SearchWritePlatformService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelReadServiceImpl implements ExcelReadService {

    private final SearchWritePlatformService searchWritePlatformService;
    private final ScoringCalculatorService scoringCalculatorService;
    private final HitRecordWritePlatformService hitRecordWritePlatformService;
    private final HitRecordRowMapper hitRecordRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void calculateScore(List<SearchDTO> searchDTOList, List<OFACData> ofacdataList) throws ExecutionException, InterruptedException {
        List<RecordDTO> listOfArrays = new ArrayList<>();

        for (SearchDTO searchDTO : searchDTOList) {
            String name = searchDTO.getName();
            Double matchingScore = searchDTO.getMatching_score();
            Integer uid          = 0;

            ModelMapper modelMapper = new ModelMapper();
            CreateSearchRequest request = modelMapper.map(searchDTO, CreateSearchRequest.class);
            Response response = this.searchWritePlatformService.createSearch(request);
            Integer searchId = (Integer) response.getId();

            listOfArrays = this.scoringCalculatorService.calculatenamewiseScore(name, matchingScore, searchId, ofacdataList).get();
            Collections.sort(listOfArrays, Comparator.comparingDouble(RecordDTO::getScore));

            Map<Integer, RecordDTO> map = new HashMap<>();
            for (RecordDTO array : listOfArrays) {
                int uniqueValue = array.getIds();
                map.put(uniqueValue, array);
            }
            List<RecordDTO> uniqueListOfArrays = new ArrayList<>(map.values());
            this.hitRecordWritePlatformService.createlistodHitData(uniqueListOfArrays ,uid);
        }
    }

    @Override
    public List<HitRecordData> fetchAllRecordData(Integer searchId) {

        final HitRecordRowMapper rowMapper = new HitRecordRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE searchId = ? ";
        Qry = Qry + whereClause;
        final List<HitRecordData> hitRecordDataList = jdbcTemplate.query(Qry, hitRecordRowMapper,
                new Object[]{searchId}
        );
        return hitRecordDataList;
    }

    @Override
    public List<Integer> fetchHitIds(Integer searchId) {
        String Qry = "SELECT id FROM hitrecord WHERE searchId = ?";
        List<Integer> hitIds = jdbcTemplate.query(Qry, new Object[]{searchId}, (rs, rowNum) -> rs.getInt("id"));
        log.info("Fetched hitIds: {}", hitIds);
        return hitIds;
    }

    @Override
    @Transactional
    public void calculateScores(List<ScreenDTO> screenDTOList, List<OFACData> ofacdataList) throws ExecutionException, InterruptedException {
        List<RecordDTO> listOfArrays = new ArrayList<>();

        for (ScreenDTO screenDTO : screenDTOList) {
            String name = screenDTO.getName();
            Double matchingScore = screenDTO.getSearchingScore();
            Integer uid = screenDTO.getUid();

            ModelMapper modelMapper = new ModelMapper();
            CreateSearchRequest request = modelMapper.map(screenDTO, CreateSearchRequest.class);
            Response response = this.searchWritePlatformService.createSearch(request);
            Integer searchId = (Integer) response.getId();

            listOfArrays = this.scoringCalculatorService.calculatenamewiseScore(name, matchingScore, searchId, ofacdataList).get();

            Collections.sort(listOfArrays, Comparator.comparingDouble(RecordDTO::getScore));

            Map<Integer, RecordDTO> map = new HashMap<>();
            for (RecordDTO array : listOfArrays) {
                int uniqueValue = array.getIds();
                map.put(uniqueValue, array);
            }
            List<RecordDTO> uniqueListOfArrays = new ArrayList<>(map.values());
            this.hitRecordWritePlatformService.createlistodHitData(uniqueListOfArrays,uid);
        }
    }
}
