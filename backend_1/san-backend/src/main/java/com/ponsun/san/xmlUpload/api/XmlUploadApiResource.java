package com.ponsun.san.xmlUpload.api;

import com.ponsun.san.xmlUpload.data.XmlUploadData;
import com.ponsun.san.xmlUpload.domain.XmlParseData;
import com.ponsun.san.xmlUpload.domain.XmlParseDataRepository;
import com.ponsun.san.xmlUpload.domain.XmlUpload;
import com.ponsun.san.xmlUpload.service.XmlUploadService;
import com.ponsun.san.xmlUpload.service.XmlUploadWritePlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/xmlUpload")
@Tag(name = "XmlUploadApiResource")
@RequiredArgsConstructor
public class XmlUploadApiResource {
    private final XmlUploadService xmlUploadService;
    private final XmlUploadWritePlatformService xmlUploadWritePlatformService;
    private final XmlParseDataRepository xmlParseDataRepository;

    @PostMapping("/file")
    public ResponseEntity<?> uploadXmlFile(@RequestParam("xmlFile") MultipartFile file,
                                           @RequestParam("uid") Integer uid,
                                           @RequestParam("pathId") Integer pathId,
                                           HttpServletRequest request) {
        try {
            String ipAddress = request.getRemoteAddr();
            XmlUpload uploadedFile = xmlUploadService.uploadXmlFile(file, uid, ipAddress, pathId);
            return ResponseEntity.ok(uploadedFile);
        } catch (RuntimeException e) {
            if ("File already exists!".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("File already exists!");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @GetMapping("/fetchAllUploadxml")
    public List<XmlUploadData> fetchAll() {
        return this.xmlUploadWritePlatformService.fetchAllUploads();
    }

    @PostMapping("/cleanupLogs")
    public ResponseEntity<String> cleanupOldLogs() {
        xmlUploadService.cleanupOldLogs();
        return ResponseEntity.ok("Old logs cleaned up successfully!");
    }

    @GetMapping("/parsed-data")
    public List<XmlParseData> fetchParsedData() {
        return xmlParseDataRepository.findAll();
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<XmlUpload> getUploadById(@PathVariable Integer id) {
        XmlUpload upload = xmlUploadService.fetchUploadById(id);
        return ResponseEntity.ok(upload);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) {
        try {
            String filePath = xmlUploadService.getFilePathById(id);
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            String fileName = path.getFileName().toString();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("File not found or error reading file: " + e.getMessage()).getBytes());
        }
    }
}
