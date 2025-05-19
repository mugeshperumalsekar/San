package com.ponsun.san.bulkAssignMapping.domain;


import com.ponsun.san.bulkAssignMapping.request.CreateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.request.UpdateBulkAssignMappingRequest;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "san_bulk_assign_mapping")
public class BulkAssignMapping extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bulkAssignId")
    private Integer bulkAssignId;

    @Column(name = "searchId")
    private Integer searchId;

        @Column(name = "hitId")
    private Integer hitId;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "euid")
    private Integer euid;

    public static BulkAssignMapping create(final CreateBulkAssignMappingRequest createBulkAssignMappingRequest) {
        final BulkAssignMapping bulkAssignMapping = new BulkAssignMapping();
        bulkAssignMapping.setBulkAssignId(createBulkAssignMappingRequest.getBulkAssignId());
        bulkAssignMapping.setSearchId(createBulkAssignMappingRequest.getSearchId());
        bulkAssignMapping.setHitId(createBulkAssignMappingRequest.getHitId());
        bulkAssignMapping.setUid(createBulkAssignMappingRequest.getUid());
        bulkAssignMapping.setStatus(Status.ACTIVE);
        bulkAssignMapping.setCreatedAt(LocalDateTime.now());
        return bulkAssignMapping;
    }

    public void update(final UpdateBulkAssignMappingRequest updateBulkAssignMappingRequest) {
        this.setBulkAssignId(updateBulkAssignMappingRequest.getBulkAssignId());
        this.setSearchId(updateBulkAssignMappingRequest.getSearchId());
        this.setHitId(updateBulkAssignMappingRequest.getHitId());
        this.setUid(updateBulkAssignMappingRequest.getUid());
        this.setStatus(Status.ACTIVE);
        this.setUpdatedAt(LocalDateTime.now());
    }

}
