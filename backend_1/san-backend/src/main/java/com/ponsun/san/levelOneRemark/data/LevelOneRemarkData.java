package com.ponsun.san.levelOneRemark.data;

import lombok.Data;

@Data
public class LevelOneRemarkData {
    private Integer id;
    private Integer criminalId;
    private Integer hitdataId;
    private Integer levelId;
    private String remark;
    private Integer searchId;
    private Integer statusId;
    private String name;
    private Integer searchingScore;

    public LevelOneRemarkData(final Integer id,final Integer criminalId,final Integer hitdataId,final Integer levelId,final String remark,final Integer searchId,final Integer statusId,final String name,final Integer searchingScore){
        this.id = id;
        this.criminalId = criminalId;
        this.hitdataId = hitdataId;
        this.levelId =levelId;
        this.remark = remark;
        this.searchId = searchId;
        this.statusId = statusId;
        this.name =name;
        this.searchingScore = searchingScore;
    }
    public static  LevelOneRemarkData newInstance(final Integer id,final Integer criminalId,final Integer hitdataId,final Integer levelId,final String remark,final Integer searchId,final Integer statusId,final String name,final Integer searchingScore){
        return new LevelOneRemarkData(id,criminalId,hitdataId,levelId,remark,searchId,statusId,name,searchingScore);
    }
}
