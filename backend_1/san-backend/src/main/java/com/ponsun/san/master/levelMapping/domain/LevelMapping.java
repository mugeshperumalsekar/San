package com.ponsun.san.master.levelMapping.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.master.levelMapping.request.CreateLevelMappingRequest;
import com.ponsun.san.master.levelMapping.request.UpdateLevelMappingRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "workflow_mapping")
public class LevelMapping extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "levelId")
    private Integer levelId;

    @Column(name = "statusId")
    private Integer statusId;

    @Column(name = "passingLevelId")
    private Integer passingLevelId;

    @Column(name = "isAlive")
    private Integer isAlive;

    @Column(name = "uid")
    private Integer uid;
    public static LevelMapping create(final CreateLevelMappingRequest createLevelMappingRequest) {
        final LevelMapping levelMapping = new LevelMapping();
        levelMapping.setLevelId(createLevelMappingRequest.getLevelId());
        levelMapping.setStatusId(createLevelMappingRequest.getStatusId());
        levelMapping.setPassingLevelId(createLevelMappingRequest.getPassingLevelId());
        levelMapping.setIsAlive(createLevelMappingRequest.getIsAlive());
        levelMapping.setUid(createLevelMappingRequest.getUid());
        levelMapping.setStatus(Status.ACTIVE);
        levelMapping.setCreatedAt(LocalDateTime.now());
        return levelMapping;
    }
    public void update(final UpdateLevelMappingRequest updateLevelMappingRequest){
        this.setLevelId(updateLevelMappingRequest.getLevelId());
        this.setStatusId(updateLevelMappingRequest.getStatusId());
        this.setPassingLevelId(updateLevelMappingRequest.getPassingLevelId());
        this.setIsAlive(updateLevelMappingRequest.getIsAlive());
        this.setUid(updateLevelMappingRequest.getUid());
        this.setStatus(Status.ACTIVE);
        this.setUpdatedAt(LocalDateTime.now());
    }
}
