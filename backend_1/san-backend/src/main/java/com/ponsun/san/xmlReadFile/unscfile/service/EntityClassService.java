package com.ponsun.san.xmlReadFile.unscfile.service;

import com.ponsun.san.infrastructure.utils.Response;

import java.util.List;
import java.util.Map;

public interface EntityClassService {

    Response saveCsvData(String filePath, String fileName);
    Response saveBulkData(List<Map<String, Object>> rows);
}
