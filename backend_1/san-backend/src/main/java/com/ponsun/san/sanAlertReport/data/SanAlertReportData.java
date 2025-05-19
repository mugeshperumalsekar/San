package com.ponsun.san.sanAlertReport.data;

import lombok.Data;

@Data
public class SanAlertReportData {
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
    private Integer uid;
    private String created_at;
    private String currentStatus;  // Add this field to hold the current status



    // Constructor that accepts all the necessary fields
    public SanAlertReportData(String name, String searchName, Integer searchId, Integer hitId, Integer criminalId,
                              String criminalName, Integer matchingScore, String remark, Integer caseId, Integer fileType, Integer uid, String created_at, String currentStatus) {
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
        this.uid = uid;
        this.created_at = created_at;
        this.currentStatus = currentStatus;
    }
}
