package com.ponsun.san.master.LevelOne.data;

import lombok.Data;

@Data
public class StatusDataMapping {
    private Integer id;
    private String name;

    public StatusDataMapping(Integer id  , String name  ) {
        this.id = id;
        this.name = name;
    }
    public static StatusDataMapping newInstance (Integer id, String name ) {
        return  new StatusDataMapping(id , name);
    }
}
