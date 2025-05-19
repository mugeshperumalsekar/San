package com.ponsun.san.excelimport.controller;

import com.ponsun.san.dto.ScreenDTO;
import com.ponsun.san.excelimport.fileupload.XlsFileParser;
import com.ponsun.san.excelimport.service.ExcelDataCommandService;
import com.ponsun.san.excelimport.service.ExcelReadService;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.data.HitRecordData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/excel")
@RequiredArgsConstructor
@Slf4j
public class ExcelController {

    private final ExcelDataCommandService excelDataCommandService;
    private final ExcelReadService excelReadService;
    private final XlsFileParser xlsFileParser;

    @PostMapping("/tableBulkImport")
    public Response tableBulkImport(@RequestParam("file") MultipartFile file) {
        XlsFileParser xlsFileParser = new XlsFileParser();
        List<Map<String, Object>> data = xlsFileParser.parseExcelDataFromStream(file);
        Response response = excelDataCommandService.saveBulkData(data);
        log.debug("File processed successfully");
        return response;
    }

    @GetMapping("/{searchId}")
    public List<HitRecordData> fetchAllRecordData(@RequestParam Integer searchId) {
        return this.excelReadService.fetchAllRecordData(searchId);
    }

    @PostMapping("/saveScreeningData")
    public ResponseEntity<Response> saveKycScreeningData(@RequestBody List<ScreenDTO> screenDTOList) {
        Response response = excelDataCommandService.saveKycScreeningData(screenDTOList);
        return ResponseEntity.ok(response);
    }
}
//    @PostMapping("/tableBulkImport")
//    public Response saveExcelDataFromFile() {
//        log.debug("START of saveExcelDataFromFile() ");
//        XlsFileParser xlsFileParser = new XlsFileParser("D:\\workspace\\file_upload", "excel_data.xlsx");
//        final List<Map<String, Object>> data = xlsFileParser.parseExcelData();
//        Response response = excelDataCommandService.saveBulkData(data);
//        log.debug("END of saveExcelDataFromFile()");
//        return response;
//    }