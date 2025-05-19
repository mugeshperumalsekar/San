package com.ponsun.san.bulkTaskAssign.services;

import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssign;

import java.util.List;

public interface BulkTaskAssignReadService {
    List<BulkTaskAssign> fetchAllBulkTaskAssign();

    BulkTaskAssign fetchBulkTaskAssignById(Integer id);
}
