package com.ponsun.san.searchLifcycle.PendingCase.data;

import lombok.Data;

@Data
public class PendingCaseData {
    private String searchName;
    private Integer caseId;
    private Integer criminalId;
    private Integer hitId;
    private Integer levelId;
    private Integer searchId;
    private Integer statusId;
    private Integer uid;
    private String criminalName;
    private Integer matchingScore;
    private String remark;
    private String createdAt;
    private Integer fileType;
    public PendingCaseData(final String searchName, final Integer caseId, final Integer criminalId,
                           final Integer hitId, final Integer levelId,
                           final Integer searchId, final Integer statusId,final Integer uid,final String criminalName,
                           final Integer matchingScore, final String remark,final String createdAt,final Integer fileType ) {
        this.searchName = searchName;
        this.caseId = caseId;
        this.criminalId = criminalId;
        this.hitId = hitId;
        this.levelId = levelId;
        this.searchId = searchId;
        this.statusId = statusId;
        this.uid=uid;
        this.criminalName = criminalName;
        this.matchingScore = matchingScore;
        this.remark = remark;
        this.createdAt = createdAt;
        this.fileType = fileType;
    }

    public static PendingCaseData newInstance(String searchName , final Integer caseId, final Integer criminalId,
                                              final Integer hitId, final Integer levelId,
                                              final Integer searchId, final Integer statusId,final Integer uid,
                                              final String criminalName,
                                              final Integer matchingScore, final String remark,final String createdAt , final Integer fileType ) {
        return new PendingCaseData(searchName, caseId, criminalId, hitId, levelId,searchId,statusId,uid,criminalName,matchingScore, remark,createdAt,fileType);
    }

}
