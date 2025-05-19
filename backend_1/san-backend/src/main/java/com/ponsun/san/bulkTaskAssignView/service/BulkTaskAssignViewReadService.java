package com.ponsun.san.bulkTaskAssignView.service;

import com.ponsun.san.bulkTaskAssignView.data.BulkTaskAssignViewData;

import java.util.List;

public interface BulkTaskAssignViewReadService {
    List<BulkTaskAssignViewData> fetchAllBulkTaskAssignView(Integer uid);
}
