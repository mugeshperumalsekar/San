package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;

import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanEntityClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsClass;

import java.util.List;

public interface UkSanEntityReadservice {
    List<UkSanEntityClass> fetchAllentity();

    List<UkSanLogsClass> getLogsByStatus();
}
