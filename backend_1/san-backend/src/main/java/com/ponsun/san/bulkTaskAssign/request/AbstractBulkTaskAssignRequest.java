package com.ponsun.san.bulkTaskAssign.request;

import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import lombok.Data;

import java.util.List;

@Data
public class AbstractBulkTaskAssignRequest {
    private  Integer assignTo;
    private Integer assignBy;
    private String searchName;
    private Integer searchId;
    private Integer isTaskAssigned;
    private Integer matchingScore;
    private Integer euid;
    private Integer uid;
    private List<BulkAssignMappingData> ofacDataList;
}
