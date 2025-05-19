package com.ponsun.san.xmlReadFile.unSanJsonImport.Api;

import com.ponsun.san.xmlReadFile.unSanJsonImport.Service.UnCsvColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/un-csv-columns")
public class UnCsvColumnController {

    @Autowired
    private UnCsvColumnService service;

    @GetMapping
    public ResponseEntity<List<String>> getColumns() throws IOException {
        return ResponseEntity.ok(service.getAllColumns());
    }

    @PostMapping("/addList")
    public ResponseEntity<List<String>> addColumns(@RequestBody String columnLine) throws IOException {
        List<String> updatedList = service.addColumnsFromLine(columnLine.trim());
        return ResponseEntity.ok(updatedList);
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

