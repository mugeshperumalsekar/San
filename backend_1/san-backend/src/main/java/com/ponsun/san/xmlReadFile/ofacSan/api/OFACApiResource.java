package com.ponsun.san.xmlReadFile.ofacSan.api;

import com.ponsun.san.xmlReadFile.ofacSan.service.OFACLogTable;
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
@RequestMapping("/api/v1/OFACApiResource")
@Tag(name="OFACApiResource")
public class OFACApiResource {
    private final com.ponsun.san.xmlReadFile.ofacSan.service.OFACCsvToDatabaseService OFACCsvToDatabaseService;
    private final OFACLogTable OFACLogTable;

    @PostMapping("/process")
    public ResponseEntity<String> processCsv(){
        try{
            OFACCsvToDatabaseService.processAllCsvFiles();
            return ResponseEntity.ok("CSV file processed successfully.");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error processing CSV file: "+e.getMessage());
        }
    }
    @GetMapping("/getAllLogs")
    public ResponseEntity<List<Map<String,Object>>> getAllLogs(){
        try{
            List<Map<String, Object>> logs = OFACLogTable.getAllLogRecords();
            return ResponseEntity.ok(logs);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
