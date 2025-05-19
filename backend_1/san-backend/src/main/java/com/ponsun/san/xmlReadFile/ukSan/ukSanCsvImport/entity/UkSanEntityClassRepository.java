package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity;

import com.ponsun.san.common.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UkSanEntityClassRepository extends JpaRepository<UkSanEntityClass, Long> {

    @Query("SELECT e FROM UkSanEntityClass e WHERE e.uniqueId IN :uniqueIds")
    List<UkSanEntityClass> findByUniqueIdIn(@Param("uniqueIds") List<String> uniqueIds);

    List<UkSanEntityClass> findByUniqueIdAndStatus(String uniqueId, Status status);

    List<UkSanEntityClass> findByStatus(Status status);

}
