package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UkSanLogsData {

    private String oldtotalRec;
    private String newtotalRec;
    private String updatedCount;
    private String totalRec;
    private String operationType;
    private String sourceFileName;
    private String performedBy;
    private String status;
    private String failureReason;
    private String processingTimeMs;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
