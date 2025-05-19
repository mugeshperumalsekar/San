package com.ponsun.san.master.levelMapping.request;

import lombok.Data;

@Data
public class AbstractLevelMappingRequest {
    private Integer levelId;
    private Integer statusId;
    private Integer passingLevelId;
    private Integer isAlive;
    private Integer uid;
}
