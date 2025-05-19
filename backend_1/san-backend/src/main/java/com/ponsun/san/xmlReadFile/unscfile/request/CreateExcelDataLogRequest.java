package com.ponsun.san.xmlReadFile.unscfile.request;



import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateExcelDataLogRequest {
    private Integer oldtotalRec;
    private Integer newtotalRec;
    private Integer updatedCount;
    private Integer totalSavedCount;
    private Integer fileHeaderCount;
    private Integer totalRec;
    private String operationType;
    private String sourceFileName;
    private String performedBy;
    private String UploadStatus;
    private String failureReason;
    private Long processingTimeMs;
    private String remarks;

}