package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AbstractLogRequest {
    private Integer id;
    private String oldtotalRec;
    private String newtotalRec;
    private String updatedCount;
    private String totalRec;
    private String alreadyExists;
    private String operationType;
    private String sourceFileName;
    private String performedBy;
    private String UploadStatus;
    private String failureReason;
    private String processingTimeMs;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
