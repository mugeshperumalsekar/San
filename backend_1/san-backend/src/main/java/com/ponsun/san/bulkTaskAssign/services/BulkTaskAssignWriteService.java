package com.ponsun.san.bulkTaskAssign.services;

import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import com.ponsun.san.bulkTaskAssign.data.BulkTaskAssignData;
import com.ponsun.san.bulkTaskAssign.request.CreateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.request.UpdateBulkTaskAssignRequest;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.data.HitRecordData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BulkTaskAssignWriteService {
    Response createBulkTaskAssign(CreateBulkTaskAssignRequest createBulkTaskAssignRequest);

    Response updateBulkTaskAssign(Integer id, UpdateBulkTaskAssignRequest updateBulkTaskAssignRequest);

    Response deactive(Integer id, Integer euid);

    List<HitRecordData> fetchAllRecordData(Integer searchId);


    List<BulkTaskAssignData> fetchAllBulkTaskAssignData();

    Response createBulkAssign(CreateBulkTaskAssignRequest createBulkTaskAssignRequest);
}
