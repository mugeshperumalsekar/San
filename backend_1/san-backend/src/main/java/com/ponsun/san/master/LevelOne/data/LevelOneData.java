package com.ponsun.san.master.LevelOne.data;

import lombok.Data;

@Data
public class LevelOneData {
    private Integer id;
    private Integer levelId;
    private String status;
    private Integer passingLevelId;
    private Integer isAlive;
    private Integer statusId;
    public LevelOneData (Integer id , Integer levelId , String status ,Integer passingLevelId , Integer isAlive , Integer statusId ) {
        this.id = id;
        this.levelId = levelId;
        this.status = status;
        this.passingLevelId = passingLevelId;
        this.isAlive = isAlive;
        this.statusId = statusId;
    }
    public static LevelOneData newInstance (Integer id,  Integer levelId , String status ,Integer passingLevelId , Integer isAlive ,Integer statusId) {
        return  new LevelOneData(id , levelId, status, passingLevelId, isAlive,statusId);
    }
}
