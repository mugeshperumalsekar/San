package com.ponsun.san.TimeReport.data;

import lombok.Data;

@Data
public class TimeReportData {
    private String searchedName;
    private String searchInsertedTime;
    private int searchInsertedSeconds;
    private int totalRelatedRecords;
    private String allHitRecordInsertedTimes;
    private String closedCaseName;
    private String caseClosedTime;
    private int caseClosedSeconds;
    private int timeToCloseSeconds;
    private int closedLevelId;
    private int closedCaseId;
    private int closedHitdataId;
    private String escalatedCaseName;
    private String escalationTime;
    private int escalationSeconds;
    private int timeToEscalationSeconds;
    private int escalatedLevelId;
    private int escalatedCaseId;
    private int escalatedHitdataId;
    private String rfiCaseName;
    private String rfiTime;
    private int timeToRfiSeconds;
    private int rfiLevelId;
    private int rfiCaseId;
    private int rfiHitdataId;
    private double averageProcessingTimeSeconds;

    public TimeReportData(String searchedName, String searchInsertedTime, int searchInsertedSeconds, int totalRelatedRecords, String allHitRecordInsertedTimes,
                          String closedCaseName, String caseClosedTime, int caseClosedSeconds, int timeToCloseSeconds, int closedLevelId, int closedCaseId, int closedHitdataId,
                          String escalatedCaseName, String escalationTime, int escalationSeconds, int timeToEscalationSeconds, int escalatedLevelId, int escalatedCaseId, int escalatedHitdataId,
                          String rfiCaseName, String rfiTime, int timeToRfiSeconds, int rfiLevelId, int rfiCaseId, int rfiHitdataId,
                          double averageProcessingTimeSeconds) {
        this.searchedName = searchedName;
        this.searchInsertedTime = searchInsertedTime;
        this.searchInsertedSeconds = searchInsertedSeconds;
        this.totalRelatedRecords = totalRelatedRecords;
        this.allHitRecordInsertedTimes = allHitRecordInsertedTimes;

        this.closedCaseName = closedCaseName;
        this.caseClosedTime = caseClosedTime;
        this.caseClosedSeconds = caseClosedSeconds;
        this.timeToCloseSeconds = timeToCloseSeconds;
        this.closedLevelId = closedLevelId;
        this.closedCaseId = closedCaseId;
        this.closedHitdataId = closedHitdataId;

        this.escalatedCaseName = escalatedCaseName;
        this.escalationTime = escalationTime;
        this.escalationSeconds = escalationSeconds;
        this.timeToEscalationSeconds = timeToEscalationSeconds;
        this.escalatedLevelId = escalatedLevelId;
        this.escalatedCaseId = escalatedCaseId;
        this.escalatedHitdataId = escalatedHitdataId;

        this.rfiCaseName = rfiCaseName;
        this.rfiTime = rfiTime;
        this.timeToRfiSeconds = timeToRfiSeconds;
        this.rfiLevelId = rfiLevelId;
        this.rfiCaseId = rfiCaseId;
        this.rfiHitdataId = rfiHitdataId;

        this.averageProcessingTimeSeconds = averageProcessingTimeSeconds;
    }

    public static TimeReportData newInstance(String searchedName, String searchInsertedTime, int searchInsertedSeconds, int totalRelatedRecords, String allHitRecordInsertedTimes,
                                             String closedCaseName, String caseClosedTime, int caseClosedSeconds, int timeToCloseSeconds, int closedLevelId, int closedCaseId, int closedHitdataId,
                                             String escalatedCaseName, String escalationTime, int escalationSeconds, int timeToEscalationSeconds, int escalatedLevelId, int escalatedCaseId, int escalatedHitdataId,
                                             String rfiCaseName, String rfiTime, int timeToRfiSeconds, int rfiLevelId, int rfiCaseId, int rfiHitdataId,
                                             double averageProcessingTimeSeconds) {
        return new TimeReportData(searchedName, searchInsertedTime, searchInsertedSeconds, totalRelatedRecords, allHitRecordInsertedTimes,
                closedCaseName, caseClosedTime, caseClosedSeconds, timeToCloseSeconds, closedLevelId, closedCaseId, closedHitdataId,
                escalatedCaseName, escalationTime, escalationSeconds, timeToEscalationSeconds, escalatedLevelId, escalatedCaseId, escalatedHitdataId,
                rfiCaseName, rfiTime, timeToRfiSeconds, rfiLevelId, rfiCaseId, rfiHitdataId,
                averageProcessingTimeSeconds);
    }
}
