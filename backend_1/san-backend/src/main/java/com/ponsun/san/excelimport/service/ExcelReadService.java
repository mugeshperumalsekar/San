package com.ponsun.san.excelimport.service;

import com.ponsun.san.EhcachePOC.Data.OFACData;
import com.ponsun.san.dto.ScreenDTO;
import com.ponsun.san.dto.SearchDTO;
import com.ponsun.san.searchLifcycle.hitrecord.data.HitRecordData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ExcelReadService {

    void calculateScore(List<SearchDTO> searchDTO, List<OFACData> ofacdataList) throws ExecutionException, InterruptedException;

    List<HitRecordData> fetchAllRecordData(Integer searchId);

    List<Integer> fetchHitIds(Integer searchId);

    void calculateScores(List<ScreenDTO> screenDTOList, List<OFACData> ofacdataList) throws ExecutionException, InterruptedException;
}
