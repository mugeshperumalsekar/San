package com.ponsun.san.xmlReadFile.unscfile.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EntityClassGenerator {
    ResponseEntity<String> generateEntityClass(String className, String tableName, List<String> columns);
}
