package com.ponsun.san.entityScreening.data;

import lombok.Data;

@Data
public class EntityScreeningData {
    private Integer id;
    private Integer criminalId;
    private Integer cycleId;
    private String display;
    private Integer matchingScore;
    private String name;
    private Integer searchId;
    private Integer statusNowId;
    private Integer kycId;

    public EntityScreeningData(final Integer id,final Integer criminalId,final Integer cycleId,final String display,final Integer matchingScore,final String name,final Integer searchId,final Integer statusNowId,final Integer kycId){
        this.id = id;
        this.criminalId = criminalId;
        this.cycleId = cycleId;
        this.display = display;
        this.matchingScore = matchingScore;
        this.name = name;
        this.searchId = searchId;
        this.statusNowId = statusNowId;
        this.kycId = kycId;
    }
    public static EntityScreeningData newInstance(final Integer id,final Integer criminalId,final Integer cycleId,final String display,final Integer matchingScore,final String name,final Integer searchId,final Integer statusNowId,final Integer kycId){
        return new EntityScreeningData(id,criminalId,cycleId,display,matchingScore,name,searchId,statusNowId,kycId);
    }
}
