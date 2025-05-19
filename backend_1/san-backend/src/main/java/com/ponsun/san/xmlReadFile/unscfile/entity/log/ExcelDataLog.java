package com.ponsun.san.xmlReadFile.unscfile.entity.log;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.xmlReadFile.unscfile.request.CreateExcelDataLogRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "un_excel_data_log")
public class ExcelDataLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "oldRec_count")
    private Integer oldtotalRec;

    @Column(name = "newRec_count")
    private Integer newtotalRec;

    @Column(name = "updated_count")
    private Integer updatedCount;

    @Column(name = "total_saved_count")
    private Integer totalSavedCount;

    @Column(name = "file_header_count")
    private Integer fileHeaderCount;

    @Column(name = "total_rec")
    private Integer totalRec;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "source_file_name")
    private String sourceFileName;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "failure_reason", length = 1000)
    private String failureReason;

    @Column(name = "processing_time_ms")
    private Long processingTimeMs;

    @Column(name = "remarks")
    private String remarks;

    public static ExcelDataLog create(final CreateExcelDataLogRequest createLogRequest) {
        final ExcelDataLog excelDataLog = new ExcelDataLog();
        excelDataLog.setOldtotalRec(createLogRequest.getOldtotalRec());
        excelDataLog.setNewtotalRec(createLogRequest.getNewtotalRec());
        excelDataLog.setUpdatedCount(createLogRequest.getUpdatedCount());
        excelDataLog.setFileHeaderCount(createLogRequest.getFileHeaderCount());
        excelDataLog.setTotalRec(createLogRequest.getTotalRec());
        excelDataLog.setTotalSavedCount(createLogRequest.getTotalSavedCount());
        excelDataLog.setOperationType(createLogRequest.getOperationType());
        excelDataLog.setSourceFileName(createLogRequest.getSourceFileName());
        excelDataLog.setPerformedBy(createLogRequest.getPerformedBy());
        excelDataLog.setFailureReason(createLogRequest.getFailureReason());
        excelDataLog.setProcessingTimeMs(createLogRequest.getProcessingTimeMs());
        excelDataLog.setRemarks(createLogRequest.getRemarks());
        excelDataLog.setCreatedAt(LocalDateTime.now());
        excelDataLog.setUpdatedAt(LocalDateTime.now());
        excelDataLog.setStatus(Status.ACTIVE);
        return excelDataLog;
    }

}