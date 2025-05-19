package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntityClass4Repository extends JpaRepository<EntityClass4, Integer> {
    @Query("SELECT e FROM EntityClass4 e WHERE e.dataid=:dataid AND e.status = 'A'")
    Optional<EntityClass4> findOneWithDataId(@Param("dataid") String dataid);
}
