package com.ponsun.san.xmlReadFile.unscfile.entity.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExcelDataLogRepository extends JpaRepository<ExcelDataLog, Integer> {
    @Query("SELECT e FROM ExcelDataLog e WHERE e.id=:id AND e.status = 'A'")
    Optional<ExcelDataLog> findOneWithDataId(@Param("id") String id);

    @Query("SELECT e FROM ExcelDataLog e WHERE e.status = 'A' ORDER BY e.createdAt DESC")
    Optional<ExcelDataLog> findLatestOne();
}

