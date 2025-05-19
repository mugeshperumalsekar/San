package com.ponsun.san.xmlReadFile.unscfile.controller;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1.EntityClass1RepositoryWrapper;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLog;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLogRepositoryWrapper;
import com.ponsun.san.xmlReadFile.unscfile.fileupload.XlsFileParser;
import com.ponsun.san.xmlReadFile.unscfile.service.EntityClassGenerator;
import com.ponsun.san.xmlReadFile.unscfile.service.EntityClassService;
import com.ponsun.san.xmlReadFile.unscfile.service.IndividualCommandService;
import com.ponsun.san.xmlReadFile.unscfile.service.UnCsvScheduler;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/unScCsv")
@Tag(name = "UnscFileController")
@RequiredArgsConstructor
@Slf4j
public class UnscFileController {

    private final EntityClassGenerator entityClassGenerator;
    private final EntityClassService entityClassService;
    private final EntityClass1RepositoryWrapper entityClass1RepositoryWrapper;
    private final ExcelDataLogRepositoryWrapper excelDataLogRepositoryWrapper;
    private final IndividualCommandService individualCommandService;
    private final UnCsvScheduler unCsvScheduler;

    @GetMapping("/generate-entity-class")
    public ResponseEntity<String>  getAllHeaders() {
        log.debug("START of saveExcelDataFromFile() ");
        XlsFileParser xlsFileParser = new XlsFileParser("D:/CSV/UN", "convertcsv.csv");
        final List<String> columns = xlsFileParser.getAllHeaders();
        ResponseEntity<String> response = entityClassGenerator.generateEntityClass( "EntityClass1",  "individual_1", columns);
        log.debug("END of saveExcelDataFromFile()");
        return response;
    }
//    @PostMapping("/Bulk-UNSC-CSVImport")
//    public Response saveBulkCsvData() throws SQLException, IOException {
//        log.debug("START of saveExcelDataFromFile() ");
////        Response response = this.entityClassService.saveCsvData("D:/CSV/UN_SC", "convertcsv.csv");
//            Response response = this.unCsvScheduler.processAllCsvFiles();
//        log.debug("END of saveExcelDataFromFile()");
//        return response;
//    }

    @PostMapping("/Bulk-UNSC-CSVImport")
    public Response saveBulkCsvData1() throws SQLException, IOException {
        log.debug("START of saveExcelDataFromFile() ");
        Response response = this.unCsvScheduler.processAllCsvFiles();
//        Response response = this.individualCommandService.saveCsvData("D:\\TableData", "convertcsv.csv");
        log.debug("END of saveExcelDataFromFile()");
        return response;
    }

    @GetMapping("/getLatestExcelDataLog")
    public ExcelDataLog findLatestOne(){
        return this.excelDataLogRepositoryWrapper.findLatestOne();
    }

    @GetMapping("/getAllExcelDataLog")
    public List<ExcelDataLog> getAllExcelDataLog(){
        return this.excelDataLogRepositoryWrapper.getAllExcelDataLog();
    }
}