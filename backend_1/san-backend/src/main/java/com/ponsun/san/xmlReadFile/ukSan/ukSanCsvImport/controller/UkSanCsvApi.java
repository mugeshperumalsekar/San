package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.controller;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanEntityClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.fileupload.XlsFileParser;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service.UkSanCsvScheduler;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service.UkSanEntityClassGenerator;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service.UkSanEntityClassService;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service.UkSanEntityReadservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ukSanCsv")
@RequiredArgsConstructor
@Slf4j
public class UkSanCsvApi {

    private final UkSanEntityClassGenerator ukSanEntityClassGenerator;
    private final UkSanEntityClassService ukSanEntityClassService;
    private final UkSanEntityReadservice ukSanEntityReadservice;
    private final UkSanCsvScheduler ukSanCsvScheduler;

    @GetMapping("/generate-entity")
    public ResponseEntity<String> getAllHeaders() {
        log.debug("START of saveExcelDataFromFile() ");
        XlsFileParser xlsFileParser = new XlsFileParser("D:/CSV/UK", "UK_Sanctions_List.csv");
        final List<String> columns = xlsFileParser.getAllHeaders();
        ResponseEntity<String> response = ukSanEntityClassGenerator.generateEntityClass("UkSanEntityClass", "sanctions_data_uk", columns);
        log.debug("END of saveExcelDataFromFile()");
        return response;
    }
//
    @PostMapping("/Bulk-UK-CSVImport")
    public Response saveExcelDataFromFile() throws SQLException, IOException {
        log.debug("START of saveExcelDataFromFile() ");
//        String filePath = "D:/CSV/UK";
//        String fileName = "UK_Sanctions_List.csv";
//
//        XlsFileParser xlsFileParser = new XlsFileParser(filePath, fileName);

//        XlsFileParser xlsFileParser = new XlsFileParser("D:\\TableData", "convertcsv.csv");
//        final List<Map<String, Object>> data = xlsFileParser.parseCsvData();
//        Response response = ukSanEntityClassService.processCsvData( filePath,  fileName);
//        Response response = ukSanEntityClassService.saveBulkData(data);

        Response response = ukSanCsvScheduler.processAllCsvFiles();
        log.debug("END of saveExcelDataFromFile()");
        return response;
    }

    @GetMapping
    public List<UkSanEntityClass> fetchAll() {
        return this.ukSanEntityReadservice.fetchAllentity();}

    @GetMapping("active")
    public List<UkSanLogsClass> fetchAllActive() {
        return this.ukSanEntityReadservice.getLogsByStatus();
    }

}