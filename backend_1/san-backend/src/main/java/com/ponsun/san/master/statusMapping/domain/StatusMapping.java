package com.ponsun.san.master.statusMapping.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.master.statusMapping.request.CreateStatusMappingRequest;
import com.ponsun.san.master.statusMapping.request.UpdateStatusMappingRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "level_status_mapping")
public class StatusMapping extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "levelId")
    private Integer levelId;

    @Column(name = "statusId")
    private Integer statusId;

    @Column(name = "uid")
    private Integer uid;

    public static StatusMapping create(final CreateStatusMappingRequest createStatusMappingRequest) {
        final StatusMapping statusMapping = new StatusMapping();
        statusMapping.setLevelId(createStatusMappingRequest.getLevelId());
        statusMapping.setStatusId(createStatusMappingRequest.getStatusId());
        statusMapping.setUid(createStatusMappingRequest.getUid());
        statusMapping.setStatus(Status.ACTIVE);
        statusMapping.setCreatedAt(LocalDateTime.now());
        return statusMapping;
    }

    public void update(final UpdateStatusMappingRequest updateStatusMappingRequest) {
        this.setLevelId(updateStatusMappingRequest.getLevelId());
        this.setStatusId(updateStatusMappingRequest.getStatusId());
        this.setUid(updateStatusMappingRequest.getUid());
        this.setStatus(Status.ACTIVE);
        this.setUpdatedAt(LocalDateTime.now());
    }
}
