package com.ponsun.san.master.statusMapping.services;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.statusMapping.data.StatusMappingData;
import com.ponsun.san.master.statusMapping.request.CreateStatusMappingRequest;
import com.ponsun.san.master.statusMapping.request.UpdateStatusMappingRequest;

import java.util.List;

public interface StatusMappingWriteService {
    Response createStatusMapping(CreateStatusMappingRequest createStatusMappingRequest);

    Response updateStatusMapping(Integer id, UpdateStatusMappingRequest updateStatusMappingRequest);

    Response blockStatusMapping(Integer id);

    Response unblockStatusMapping(Integer id);

    Response createOrUpdateStatusMapping(Integer levelId, Integer statusId, CreateStatusMappingRequest createStatusMappingRequest);

    List<StatusMappingData> fetchStatusMappingData(Integer levelId, Integer statusId);
}

