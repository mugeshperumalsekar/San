package com.ponsun.san.xmlUpload.data;

import lombok.Data;

@Data
public class XmlUploadData {
    private Integer id;
    private String fileName;
    private Long fileSize;
    private Integer recordCount;
    private Double processingTime;
    private String fileType;
    private String createdAt;
    private String userName;
    private String status;

    public XmlUploadData(final Integer id, final String fileName, final Long fileSize, final Integer recordCount, final Double processingTime, final String fileType, final String createdAt, final String userName, final String status) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.recordCount = recordCount;
        this.processingTime = processingTime;
        this.fileType = fileType;
        this.createdAt = createdAt;
        this.userName = userName;
        this.status = status;
    }

    public static XmlUploadData newInstance(final Integer id, final String fileName, final Long fileSize, final Integer recordCount, final Double processingTime, final String fileType, final String createdAt, final String userName, final String status) {
        return new XmlUploadData(id, fileName, fileSize, recordCount, processingTime, fileType, createdAt, userName, status);
    }
}
