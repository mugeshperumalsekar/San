package com.ponsun.san.searchLifcycle.hitrecord.services;

import com.ponsun.san.dto.RecordDTO;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.domain.HitRecord;
import com.ponsun.san.searchLifcycle.hitrecord.request.CreateHitRecordRequest;
import com.ponsun.san.searchLifcycle.hitrecord.request.UpdateHitRecordRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HitRecordWritePlatformService {
    Response createHitData(CreateHitRecordRequest createHitDataRequest);
    Response updateHitdata(Integer id, UpdateHitRecordRequest updateHitDataRequest);
    Response blockHitData(Integer id);
    Response unblockHitData(Integer id);
    List<HitRecord> createlistodHitData(List<RecordDTO> uniqueListOfArrays, Integer uid);
}