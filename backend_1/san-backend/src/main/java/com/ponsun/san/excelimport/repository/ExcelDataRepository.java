package com.ponsun.san.excelimport.repository;

import com.ponsun.san.excelimport.entity.ExcelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData, Integer> {
    List<ExcelData> findByName(String name);
}
