package com.ponsun.san.LevelFlow.data;

import lombok.Data;

@Data
public class LevelFlowData {
    private String name;
    private String searchName;
    private Integer searchId;
    private Integer hitId;
    private Integer criminalId;
    private String criminalName;
    private Integer matchingScore;
    private String remark;
    private Integer caseId;
    private Integer fileType;


    public LevelFlowData(String name, String searchName, Integer searchId, Integer hitId, Integer criminalId, String criminalName, Integer matchingScore, String remark,Integer caseId,Integer fileType) {
        this.name = name;
        this.searchName = searchName;
        this.searchId = searchId;
        this.hitId = hitId;
        this.criminalId = criminalId;
        this.criminalName = criminalName;
        this.matchingScore = matchingScore;
        this.remark = remark;
        this.caseId = caseId;
        this.fileType = fileType;
    }
    public static LevelFlowData newInstance(String name, String searchName, Integer searchId, Integer hitId, Integer criminalId, String criminalName, Integer matchingScore, String remark,Integer caseId,Integer fileType){
        return new LevelFlowData(name,searchName,searchId,hitId,criminalId,criminalName,matchingScore,remark,caseId,fileType);
    }

}
