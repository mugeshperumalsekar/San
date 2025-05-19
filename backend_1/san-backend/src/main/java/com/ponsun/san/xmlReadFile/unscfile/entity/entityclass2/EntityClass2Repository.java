package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntityClass2Repository extends JpaRepository<EntityClass2, Integer> {

    @Query("SELECT e FROM EntityClass2 e WHERE e.dataid=:dataid AND e.status = 'A'")
    Optional<EntityClass2> findOneWithDataId(@Param("dataid") String dataid);
}
