package com.ponsun.san.master.statusMapping.request;

import lombok.Data;

@Data
public class AbstractStatusMappingRequest {
    private Integer levelId;
    private Integer statusId;
    private Integer uid;
}
