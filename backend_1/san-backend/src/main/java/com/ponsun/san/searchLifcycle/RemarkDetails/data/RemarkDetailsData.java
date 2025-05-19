package com.ponsun.san.searchLifcycle.RemarkDetails.data;

import lombok.Data;

@Data

public class RemarkDetailsData {
    private String remark;
    private String createdAt;
    private String level;
    private String status;

    public RemarkDetailsData(final String remark,final String createdAt,final String level,final String status){
        this.remark = remark;
        this.createdAt = createdAt;
        this.level = level;
        this.status = status;
    }
    public static RemarkDetailsData newInstance(final String remark,final String createdAt,final String level,final String status){
        return new RemarkDetailsData(remark, createdAt, level, status);
    }
}
