package com.ponsun.san.master.level.data;

import lombok.Data;

@Data
public class LevelData {

    private Integer id;
    private String name;
    private Integer uid;

    public LevelData (final Integer id , final String name , final Integer uid) {
        this.id = id;
        this.name = name;
        this.uid = uid;
    }

    public static LevelData newInstance (final Integer id , final String name , final Integer uid) {
        return new LevelData(id , name , uid);
    }


}
