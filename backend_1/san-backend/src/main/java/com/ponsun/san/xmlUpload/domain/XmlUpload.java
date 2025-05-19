package com.ponsun.san.xmlUpload.domain;

import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "xml_uploads")
public class XmlUpload extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "record_count")
    private Integer recordCount;

    @Column(name = "xml_schema_version")
    private String xmlSchemaVersion;

    @Column(name = "processing_time")
    private Double processingTime;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "pathId")
    private Integer pathId;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "valid")
    private boolean valid;
}
