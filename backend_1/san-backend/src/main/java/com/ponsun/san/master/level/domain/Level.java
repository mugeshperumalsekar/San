package com.ponsun.san.master.level.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.master.level.request.CreateLevelRequest;
import com.ponsun.san.master.level.request.UpdateLevelRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "level")
public class Level extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "uid")
    private Integer uid;

    public static Level create(final CreateLevelRequest createLevelRequest) {
        final Level level = new Level();
        level.setName(createLevelRequest.getName());
        level.setUid(createLevelRequest.getUid());
        level.setStatus(Status.ACTIVE);
        level.setCreatedAt(LocalDateTime.now());
        return level;
    }

    public void update(final UpdateLevelRequest updateLevelRequest) {
        this.setName(updateLevelRequest.getName());
        this.setUid(updateLevelRequest.getUid());
        this.setStatus(Status.ACTIVE);
        this.setUpdatedAt(LocalDateTime.now());
    }
}
