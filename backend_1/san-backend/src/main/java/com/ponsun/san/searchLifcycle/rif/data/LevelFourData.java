package com.ponsun.san.searchLifcycle.rif.data;

import lombok.Data;

@Data
public class LevelFourData {

    private String searchName;
    private Integer searchId;
    private Integer criminalId;
    private Integer hitId;
    private Integer caseId;
    private String criminalName;
    private String remark;
    private Integer matchScore;
    private String country;
    private String state;
    private String dob;
    private String levelId;
    private Integer fileType;


    public LevelFourData(final String searchName ,  final Integer searchId,final Integer criminalId,final Integer hitId,final Integer caseId,final String criminalName,final String remark,final String levelId,final Integer matchScore,final String country,final String state,final String dob , final Integer fileType){

        this.searchName = searchName;
        this.searchId = searchId;
        this.criminalId = criminalId;
        this.hitId = hitId;
        this.caseId = caseId;
        this.criminalName = criminalName;
        this.remark = remark;
        this.matchScore = matchScore;
        this.country = country;
        this.state = state;
        this.dob = dob;
        this.levelId = levelId;
        this.fileType = fileType;
    }

    public static  LevelFourData newInstance(final String searchName, final Integer searchId,final Integer criminalId,final Integer hitId,final Integer caseId,final String criminalName,final String remark,final String levelId,final Integer matchScore,final String country,final String state,final String dob  , final Integer fileType){
        return new LevelFourData(searchName,searchId, criminalId, hitId, caseId, criminalName,remark, levelId, matchScore, country, state, dob , fileType);
    }
}
