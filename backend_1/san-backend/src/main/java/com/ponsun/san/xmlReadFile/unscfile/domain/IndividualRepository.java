package com.ponsun.san.xmlReadFile.unscfile.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Integer> {

    @Query("SELECT e FROM Individual e WHERE e.dataId=:dataId AND e.status = 'A'")
    Optional<Individual> findOneWithDataId(@Param("dataId") String dataId);

    @Query(value = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = :tableName", nativeQuery = true)
    int getColumnCount(@Param("tableName") String tableName);
}