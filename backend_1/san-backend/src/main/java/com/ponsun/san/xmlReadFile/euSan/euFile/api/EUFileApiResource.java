package com.ponsun.san.xmlReadFile.euSan.euFile.api;

import com.ponsun.san.xmlReadFile.euSan.euFile.service.EULogTable;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/EUFileApiResource")
@Tag(name = "EUFileApiResource")
public class EUFileApiResource {
    private final com.ponsun.san.xmlReadFile.euSan.euFile.service.EUCsvToDatabaseService EUCsvToDatabaseService;
    private final EULogTable EULogTable;

    @PostMapping("/process")
    public ResponseEntity<String> processCsv() {
        try {
            EUCsvToDatabaseService.processAllCsvFiles();
            return ResponseEntity.ok("CSV file processed successfully.");
        } catch (Exception e) { // Catch generic exceptions
            return ResponseEntity.status(500).body("Error processing CSV file: " + e.getMessage());
        }
    }

    @GetMapping("/getAllLogs")
    public ResponseEntity<List<Map<String, Object>>> getAllLogs() {
        try {
            List<Map<String, Object>> logs = EULogTable.getAllLogRecords();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}