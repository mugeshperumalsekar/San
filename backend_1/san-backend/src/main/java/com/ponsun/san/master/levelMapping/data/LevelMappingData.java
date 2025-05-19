package com.ponsun.san.master.levelMapping.data;

import lombok.Data;

@Data
public class LevelMappingData {
    private Integer id;
    private Integer levelId;
    private Integer statusId;
    private Integer passingLevelId;
    private Integer isAlive;
    private Integer uid;

    public LevelMappingData (final Integer id , final Integer levelId , final Integer statusId , final Integer passingLevelId , final Integer isAlive , final Integer uid) {
        this.id = id;
        this.levelId = levelId;
        this.statusId = statusId;
        this.passingLevelId = passingLevelId;
        this.isAlive = isAlive;
        this.uid   = uid;
    }

    public static LevelMappingData newInstance (final Integer id , final Integer levelId , final Integer statusId , final Integer passingLevelId , final Integer isAlive , final Integer uid) {
        return new LevelMappingData(id, levelId, statusId, passingLevelId, isAlive,uid);
    }
}
