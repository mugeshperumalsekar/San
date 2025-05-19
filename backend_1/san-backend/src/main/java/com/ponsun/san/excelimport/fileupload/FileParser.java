package com.ponsun.san.excelimport.fileupload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileParser {
    List<Map<String, Object>> parseExcelDataFromStream(MultipartFile file);
}
