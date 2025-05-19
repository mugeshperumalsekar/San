package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity;


import com.ponsun.san.common.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UkSanLogsRepository extends JpaRepository<UkSanLogsClass, Integer> {
    List<UkSanLogsClass> findByStatus(Status status);
}