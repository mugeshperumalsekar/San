package com.ponsun.san.searchLifcycle.PendingAlert.data;

import lombok.Data;

@Data
public class PendingAlertData {

    private String searchName;
    private Integer searchId;
    private Integer hitId;
    private Integer criminalId;
    private String criminalName;
    private Integer matchingScore;
    private String remark;
    private String createdAt;
    private Integer fileType;


    public PendingAlertData(final String searchName ,   final Integer searchId,final Integer hitId,final Integer criminalId,final String name,final Integer matchingScore,final String remark,final String createdAt , final Integer fileType){

        this.searchName = searchName;
        this.searchId = searchId;
        this.hitId = hitId;
        this.criminalId = criminalId;
        this.criminalName = name;
        this.matchingScore = matchingScore;
        this.remark = remark;
        this.createdAt = createdAt;
        this.fileType = fileType;

    }
    public  static PendingAlertData newInstance(final String searchName , final Integer searchId,final Integer hitId,final Integer criminalId,final String criminalName,final Integer matchingScore,final String remark,final String createdAt , Integer fileType){
        return new PendingAlertData(searchName,searchId, hitId, criminalId, criminalName, matchingScore, remark,createdAt , fileType);
    }
}
