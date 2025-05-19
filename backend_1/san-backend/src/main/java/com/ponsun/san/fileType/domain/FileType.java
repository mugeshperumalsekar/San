package com.ponsun.san.fileType.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.fileType.request.CreateFileTypeRequest;
import com.ponsun.san.fileType.request.UpdateFileTypeRequest;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="file_type")
public class FileType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "euid")
    private Integer euid;

    public static FileType create(final CreateFileTypeRequest createFileTypeRequest){
        final FileType fileType = new FileType();
        fileType.setName(createFileTypeRequest.getName());
        fileType.setValid(createFileTypeRequest.getValid());
        fileType.setUid(createFileTypeRequest.getUid());
        fileType.setStatus(Status.ACTIVE);
        fileType.setCreatedAt(LocalDateTime.now());
        return fileType;
    }

    public void update(final UpdateFileTypeRequest updateFileTypeRequest){
        this.setName(updateFileTypeRequest.getName());
        this.setValid(updateFileTypeRequest.getValid());
        this.setEuid(updateFileTypeRequest.getEuid());
        this.setStatus(Status.ACTIVE);
        this.setUpdatedAt(LocalDateTime.now());
    }
}
