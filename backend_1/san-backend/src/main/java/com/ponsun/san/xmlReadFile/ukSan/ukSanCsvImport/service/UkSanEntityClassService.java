package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;

import com.ponsun.san.infrastructure.utils.Response;

public interface UkSanEntityClassService {
    //    Response saveBulkData(List<Map<String, Object>> rows);
    Response processCsvData(String filePath, String fileName);

}
