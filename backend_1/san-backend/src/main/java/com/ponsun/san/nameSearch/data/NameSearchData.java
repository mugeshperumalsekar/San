package com.ponsun.san.nameSearch.data;

import lombok.Data;

@Data
public class NameSearchData {
    private Integer screeningType;
    private Integer kycId;
    private String question;
    private String answer;
    private Integer applicantFormId;
    private Integer isScreening;

    public NameSearchData (Integer screeningType,Integer kycId,String question, String answer ,  Integer applicantFormId , Integer isScreening) {
        this.kycId = kycId;
        this.screeningType = screeningType;
        this.question = question;
        this.answer = answer;
        this.applicantFormId = applicantFormId;
        this.isScreening = isScreening;
    }
    public static NameSearchData newInstance (Integer screeningType,Integer kycId,String question, String answer ,  Integer applicantFormId , Integer isScreening) {
        return new NameSearchData(screeningType , kycId , question,answer ,applicantFormId,isScreening );
    }
}
