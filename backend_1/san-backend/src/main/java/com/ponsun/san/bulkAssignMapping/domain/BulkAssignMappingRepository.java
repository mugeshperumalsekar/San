package com.ponsun.san.bulkAssignMapping.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BulkAssignMappingRepository extends JpaRepository<BulkAssignMapping,Integer> {
    Optional<BulkAssignMapping> findById(Integer id);
}
