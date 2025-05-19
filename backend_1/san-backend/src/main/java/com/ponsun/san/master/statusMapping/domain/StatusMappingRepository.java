package com.ponsun.san.master.statusMapping.domain;


import com.ponsun.san.common.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusMappingRepository extends JpaRepository<StatusMapping, Integer> {
    Optional<StatusMapping> findById(Integer id);
    boolean existsByLevelIdAndStatusId( Integer levelId , Integer statusId);

    List<StatusMapping> findByLevelIdAndStatusId(Integer levelId, Integer statusId);

    List<StatusMapping> findByStatus(Status status);

}