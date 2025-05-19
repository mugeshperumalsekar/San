package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.fileupload;

import java.util.List;
import java.util.Map;

public interface FileParser {
    List<Map<String, Object>> parseExcelData();
}
