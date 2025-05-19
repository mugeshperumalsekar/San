package com.ponsun.san.master.levelMapping.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.master.statusMapping.domain.StatusMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelMappingRepository extends JpaRepository<LevelMapping, Integer> {
    Optional<LevelMapping> findById(Integer id);
    boolean existsByLevelIdAndStatusId( Integer levelId , Integer statusId);

    List<LevelMapping> findByLevelIdAndStatusId(Integer levelId, Integer statusId);

    List<LevelMapping> findByStatus(Status status);



}