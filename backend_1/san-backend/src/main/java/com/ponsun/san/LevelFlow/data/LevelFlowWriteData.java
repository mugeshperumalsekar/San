package com.ponsun.san.LevelFlow.data;
import lombok.Data;

@Data
public class LevelFlowWriteData {
    private Integer search_id ;
    private Integer hitdata_id ;
    private Integer level_id ;
    private Integer caseId ;
    private Integer criminal_id ;
    private Integer statusId ;
    private Integer passingLevelId  ;
    private Integer isAlive  ;
    private Boolean valid;
    private String remark;
    private Integer uid;

    public LevelFlowWriteData(Integer search_id, Integer hitdata_id, Integer level_id, Integer case_id, Integer criminal_id, Integer statusId, Integer passingLevelId, Integer isAlive, String remark, Boolean valid,Integer uid) {
        this.search_id = search_id;
        this.hitdata_id = hitdata_id;
        this.level_id = level_id;
        this.caseId = case_id;
        this.criminal_id = criminal_id;
        this.statusId = statusId;
        this.passingLevelId = passingLevelId;
        this.isAlive = isAlive;
        this.remark = remark;
        this.valid = valid;
        this.uid=uid;
    }
    public static LevelFlowWriteData newInstance(Integer search_id, Integer hitdata_id, Integer level_id, Integer case_id, Integer criminal_id, Integer statusId, Integer passingLevelId, Integer isAlive, String remark, Boolean valid,Integer uid){
        return new LevelFlowWriteData(search_id,hitdata_id,level_id,case_id,criminal_id,statusId,passingLevelId,isAlive,remark,valid,uid);
    }

}
