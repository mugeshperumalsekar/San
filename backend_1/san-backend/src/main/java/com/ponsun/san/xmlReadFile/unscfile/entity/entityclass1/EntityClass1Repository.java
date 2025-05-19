package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntityClass1Repository extends JpaRepository<EntityClass1, Integer> {

    @Query("SELECT e FROM EntityClass1 e WHERE e.dataid=:dataid AND e.status = 'A'")
    Optional<EntityClass1> findOneWithDataId(@Param("dataid") String dataid);

    @Query(value = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = :tableName", nativeQuery = true)
    int getColumnCount(@Param("tableName") String tableName);
}
