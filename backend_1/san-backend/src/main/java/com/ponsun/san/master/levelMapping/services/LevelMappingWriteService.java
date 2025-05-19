package com.ponsun.san.master.levelMapping.services;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.levelMapping.data.LevelMappingData;
import com.ponsun.san.master.levelMapping.request.CreateLevelMappingRequest;
import com.ponsun.san.master.levelMapping.request.UpdateLevelMappingRequest;

import java.util.List;

public interface LevelMappingWriteService {
    Response createLevelMapping(CreateLevelMappingRequest createLevelMappingRequest);

    Response updateLevelMapping(Integer id, UpdateLevelMappingRequest updateLevelMappingRequest);

    Response blockLevelMapping(Integer id);

    Response unblockLevelMapping(Integer id);

    Response createOrUpdateLevelMapping(Integer levelId, Integer LevelId, CreateLevelMappingRequest createLevelMappingRequest);

    List<LevelMappingData> fetchLevelMappingData(Integer levelId, Integer statusId);
}
