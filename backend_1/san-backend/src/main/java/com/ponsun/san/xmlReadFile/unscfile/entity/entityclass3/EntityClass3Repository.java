package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntityClass3Repository extends JpaRepository<EntityClass3, Integer> {

    @Query("SELECT e FROM EntityClass3 e WHERE e.dataid=:dataid AND e.status = 'A'")
    Optional<EntityClass3> findOneWithDataId(@Param("dataid") String dataid);
}
