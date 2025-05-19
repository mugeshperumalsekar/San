package com.ponsun.san.xmlReadFile.ukSan.ukSanJsonImport.Api;

import com.ponsun.san.xmlReadFile.ukSan.ukSanJsonImport.Service.CsvColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/csv-columns")
public class CsvColumnController {

    @Autowired
    private CsvColumnService service;

    @GetMapping
    public ResponseEntity<List<String>> getColumns() throws IOException {
        return ResponseEntity.ok(service.getAllColumns());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addColumn(@RequestBody String column) throws IOException {
        service.addColumn(column.trim());
        return ResponseEntity.ok("Column added successfully");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeColumn(@RequestParam String column) throws IOException {
        service.removeColumn(column.trim());
        return ResponseEntity.ok("Column removed successfully");
    }
}

