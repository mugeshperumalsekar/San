package com.ponsun.san.master.statusMapping.data;

import lombok.Data;

@Data
public class StatusMappingData {
    private Integer id;
    private Integer levelId;
    private Integer statusId;
    private Integer uid;

    public StatusMappingData(final Integer id , final Integer levelId , final Integer statusId , final Integer uid) {
        this.id = id;
        this.levelId = levelId;
        this.statusId = statusId;
        this.uid   = uid;
    }

    public static StatusMappingData newInstance (final Integer id , final Integer levelId , final Integer statusId ,  final Integer uid) {
        return new StatusMappingData(id, levelId, statusId, uid);
    }
}
