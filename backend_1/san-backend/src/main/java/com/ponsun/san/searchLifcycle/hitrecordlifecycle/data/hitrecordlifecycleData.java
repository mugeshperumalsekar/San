package com.ponsun.san.searchLifcycle.hitrecordlifecycle.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class hitrecordlifecycleData {

    private Integer id;
    private Integer searchId;
    private Integer hitdataId;
    private Integer criminalId;
    private Integer caseId;
    private Integer levelId;
    private Integer statusId;
    private Integer uid;
    private LocalDateTime dt;
    private Boolean valid;
    private String remark;
    private Integer isAlive  ;
    private Integer passingLevelId;

    public hitrecordlifecycleData(final Integer id, final Integer searchId, final Integer hitdataId, final Integer criminalId, final Integer caseId, final Integer levelId, final Integer statusId, final Integer uid, final LocalDateTime dt, final Boolean valid, final String remark,final Integer isAlive, final Integer passingLevelId) {
        this.id = id;
        this.searchId = searchId;
        this.hitdataId = hitdataId;
        this.criminalId = criminalId;
        this.caseId = caseId;
        this.levelId = levelId;
        this.statusId = statusId;
        this.uid = uid;
        this.dt = dt;
        this.valid = valid;
        this.remark = remark;
        this.isAlive=isAlive;
        this.passingLevelId=passingLevelId;

    }

    public hitrecordlifecycleData() {

    }

    public static hitrecordlifecycleData newInstance(final Integer id, final Integer searchId, final Integer hitdataId, final Integer criminalId, final Integer caseId, final Integer levelId, final Integer statusId, final Integer uid, final LocalDateTime dt, final Boolean valid, final String remark,final Integer isAlive, final Integer passingLevelId) {
        return new hitrecordlifecycleData(id, searchId, hitdataId, criminalId, caseId, levelId, statusId, uid, dt, valid, remark,isAlive,passingLevelId);
    }
}
