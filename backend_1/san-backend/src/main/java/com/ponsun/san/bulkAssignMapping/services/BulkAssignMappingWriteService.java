package com.ponsun.san.bulkAssignMapping.services;

import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import com.ponsun.san.bulkAssignMapping.request.CreateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.request.UpdateBulkAssignMappingRequest;
import com.ponsun.san.infrastructure.utils.Response;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BulkAssignMappingWriteService {
    Response createBulkAssignMapping(CreateBulkAssignMappingRequest createBulkAssignMappingRequest);

    Response updateBulkAssignMapping(Integer id, UpdateBulkAssignMappingRequest updateBulkAssignMappingRequest);

    Response deactive(Integer id, Integer euid);

    List<BulkAssignMappingData> fetchAllRecordData(Integer searchId);

    @Transactional
    List<BulkAssignMappingData> getHitRecordsBySearchId(Integer searchId);
}
