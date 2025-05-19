package com.ponsun.san.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScreenDTO {
    private String name;
    private double searchingScore;
    private Integer uid;
    private Integer kycId;
    private Integer applicantFormId;
    private Integer isScreening;
    private Integer screeningType;

    private ScreenDTO (String name,double searchingScore,Integer uid,Integer kycId , Integer applicantFormId , Integer isScreening , Integer screeningType){
        this.name = name;
        this.searchingScore = searchingScore;
        this.uid = uid;
        this.kycId = kycId;
        this.applicantFormId = applicantFormId;
        this.isScreening = isScreening;
        this.screeningType = screeningType;
    }
    public static ScreenDTO newInstance (String name,double searchingScore,Integer uid,Integer kycId , Integer applicantFormId , Integer isScreening , Integer screeningType){
        return  new ScreenDTO(name,searchingScore,uid,kycId,applicantFormId,isScreening , screeningType);
    }

}
