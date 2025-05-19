package com.ponsun.san.bulkAssignMapping.request;

import lombok.Data;

@Data
public class AbstractBulkAssignMappingRequest {
    private  Integer bulkAssignId;
    private Integer searchId;
    private Integer hitId;
    private Integer fileType;
    private Integer euid;
    private Integer uid;
}
