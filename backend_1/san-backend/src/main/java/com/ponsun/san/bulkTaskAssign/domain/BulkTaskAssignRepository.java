package com.ponsun.san.bulkTaskAssign.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BulkTaskAssignRepository extends JpaRepository<BulkTaskAssign,Integer> {
    Optional<BulkTaskAssign> findById(Integer id);
}
