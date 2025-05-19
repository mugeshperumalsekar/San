package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.request.CreateLogRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "uk_excel_data_log")
public class UkSanLogsClass extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "oldRec_count")
    private String oldtotalRec;

    @Column(name = "newRec_count")
    private String newtotalRec;

    @Column(name = "updated_count")
    private String updatedCount;

    @Column(name = "total_rec")
    private String totalRec;

    @Column(name = "already_exists")
    private String alreadyExists;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "source_file_name")
    private String sourceFileName;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "uploadStatus")
    private String uploadStatus;

    @Column(name = "failure_reason", length = 1000)
    private String failureReason;

    @Column(name = "processing_time_ms")
    private String processingTimeMs;

    @Column(name = "remarks")
    private String remarks;

    public static UkSanLogsClass create(final CreateLogRequest createLogRequest) {
        final UkSanLogsClass ukSanLogsClass = new UkSanLogsClass();
        ukSanLogsClass.setOldtotalRec(createLogRequest.getOldtotalRec());
        ukSanLogsClass.setNewtotalRec(createLogRequest.getNewtotalRec());
        ukSanLogsClass.setUpdatedCount(createLogRequest.getUpdatedCount());
        ukSanLogsClass.setAlreadyExists(createLogRequest.getAlreadyExists());
        ukSanLogsClass.setTotalRec(createLogRequest.getTotalRec());
        ukSanLogsClass.setOperationType(createLogRequest.getOperationType());
        ukSanLogsClass.setSourceFileName(createLogRequest.getSourceFileName());
        ukSanLogsClass.setPerformedBy(createLogRequest.getPerformedBy());
        ukSanLogsClass.setFailureReason(createLogRequest.getFailureReason());
        ukSanLogsClass.setProcessingTimeMs(createLogRequest.getProcessingTimeMs());
        ukSanLogsClass.setRemarks(createLogRequest.getRemarks());
//        ukSanLogsClass.setCreatedAt(LocalDateTime.now());
//        ukSanLogsClass.setUpdatedAt(LocalDateTime.now());
        ukSanLogsClass.setUploadStatus(createLogRequest.getUploadStatus());
        ukSanLogsClass.onCreate();
        ukSanLogsClass.setStatus(Status.ACTIVE);
        return ukSanLogsClass;
    }

}