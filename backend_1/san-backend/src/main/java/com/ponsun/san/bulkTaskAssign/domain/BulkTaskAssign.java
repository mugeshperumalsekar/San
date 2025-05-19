package com.ponsun.san.bulkTaskAssign.domain;


import com.ponsun.san.bulkTaskAssign.request.CreateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.request.UpdateBulkTaskAssignRequest;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "san_bulk_task_assign")
public class BulkTaskAssign extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "assignTo")
    private Integer assignTo;

    @Column(name = "assignBy")
    private Integer assignBy;

    @Column(name = "searchName")
    private String searchName;

    @Column(name = "searchId")
    private Integer searchId;

    @Column(name = "isTaskAssigned")
    private Integer isTaskAssigned;

    @Column(name = "matchingScore")
    private Integer matchingScore;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "euid")
    private Integer euid;

    public static BulkTaskAssign create(final CreateBulkTaskAssignRequest createBulkTaskAssignRequest) {
        final BulkTaskAssign bulkTaskAssign = new BulkTaskAssign();
        bulkTaskAssign.setAssignTo(createBulkTaskAssignRequest.getAssignTo());
        bulkTaskAssign.setAssignBy(createBulkTaskAssignRequest.getAssignBy());
        bulkTaskAssign.setSearchName(createBulkTaskAssignRequest.getSearchName());
        bulkTaskAssign.setSearchId(createBulkTaskAssignRequest.getSearchId());
        bulkTaskAssign.setMatchingScore(createBulkTaskAssignRequest.getMatchingScore());
        bulkTaskAssign.setIsTaskAssigned(1);
        bulkTaskAssign.setUid(createBulkTaskAssignRequest.getUid());
        bulkTaskAssign.setStatus(Status.ACTIVE);
        bulkTaskAssign.setCreatedAt(LocalDateTime.now());
        return bulkTaskAssign;
    }

    public void update(final UpdateBulkTaskAssignRequest updateBulkTaskAssignRequest) {
        this.setAssignTo(updateBulkTaskAssignRequest.getAssignTo());
        this.setAssignBy(updateBulkTaskAssignRequest.getAssignBy());
        this.setSearchName(updateBulkTaskAssignRequest.getSearchName());
        this.setSearchId(updateBulkTaskAssignRequest.getSearchId());
        this.setMatchingScore(updateBulkTaskAssignRequest.getMatchingScore());
        this.setUid(updateBulkTaskAssignRequest.getUid());
        this.setStatus(Status.ACTIVE);
        this.setUpdatedAt(LocalDateTime.now());
    }

}
