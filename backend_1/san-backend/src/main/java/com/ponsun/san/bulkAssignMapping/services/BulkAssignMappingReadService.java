package com.ponsun.san.bulkAssignMapping.services;

import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMapping;

import java.util.List;

public interface BulkAssignMappingReadService {
    List<BulkAssignMapping> fetchAllBulkAssignMapping();

    BulkAssignMapping fetchBulkAssignMappingById(Integer id);
}
