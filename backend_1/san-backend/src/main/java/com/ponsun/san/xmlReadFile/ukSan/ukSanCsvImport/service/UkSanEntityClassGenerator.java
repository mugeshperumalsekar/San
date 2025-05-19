package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UkSanEntityClassGenerator {
    ResponseEntity<String> generateEntityClass(String className, String tableName, List<String> columns);
}
