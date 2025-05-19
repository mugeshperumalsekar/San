package com.ponsun.san.xmlUpload.service;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.xmlUpload.data.XmlUploadData;
import com.ponsun.san.xmlUpload.domain.*;
import com.ponsun.san.xmlUpload.rowMapper.XmlUploadRowMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.time.*;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class XmlUploadService implements XmlUploadWritePlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final XmlUploadRowMapper xmlUploadRowMapper;
    private final XmlUploadRepository xmlUploadRepository;
    private final XmlUploadLogRepository xmlUploadLogRepository;
    private final XmlParseDataRepository xmlParseDataRepository;
    @Value("${app.upload.path}")
    private String uploadBasePath;

    public XmlUpload uploadXmlFile(MultipartFile file, Integer uid, String ipAddress, Integer pathId) {
        boolean fileExists = xmlUploadRepository.existsByFileNameAndUidAndPathId(file.getOriginalFilename(), uid, pathId);
        if (fileExists) {
            throw new RuntimeException("File already exists!");
        }
        XmlUpload upload = new XmlUpload();
        upload.setFileName(file.getOriginalFilename());
        upload.setFileSize(file.getSize());
        upload.setIpAddress(ipAddress);
        upload.setUid(uid);
        upload.setStatus(Status.ACTIVE);
        upload.setValid(true);

        String pathType = switch (pathId) {
            case 1 -> "1st Path";
            case 2 -> "2nd Path";
            case 3 -> "3rd Path";
            case 4 -> "4th Path";
            default -> "Unknown Path";
        };

        upload.setPathId(pathId);
        upload = xmlUploadRepository.save(upload);
        Instant startTime = Instant.now();
        boolean success = true;
        String errorMsg = null;
        try {
            String folderPath;
            String fileExtension;
            String originalFileName = file.getOriginalFilename();
            String baseFileName = originalFileName != null ? originalFileName.replaceFirst("[.][^.]+$", "") : "default";

            if (pathId == 1) {
                folderPath = "D:\\CSV\\OFAC";
                fileExtension = ".csv";
            } else if (pathId == 2) {
                folderPath = "D:\\CSV\\EU";
                fileExtension = ".csv";
            } else if (pathId == 3) {
                folderPath = "D:\\CSV\\UK";
                fileExtension = ".csv";
            } else if (pathId == 4) {
                folderPath = "D:\\CSV\\UN";
                fileExtension = ".csv";
            } else {
                folderPath = "D:\\CSV\\others";
                fileExtension = ".txt";
            }
            File directory = new File(folderPath);
            if (!directory.exists()) directory.mkdirs();

            // Save file locally
            File localFile = new File(folderPath + "\\" + file.getOriginalFilename());
            if (localFile.exists()) {
                throw new RuntimeException("File already exists at destination: " + localFile.getAbsolutePath());
            }
            try (FileOutputStream fos = new FileOutputStream(localFile)) {
                fos.write(file.getBytes());
            }
            int recordCount = 0;
            if (fileExtension.equals(".xml")) {

                // --- XML Processing ---
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                var document = builder.parse(file.getInputStream());
                document.getDocumentElement().normalize();
                Element root = document.getDocumentElement();
                NodeList childNodes = root.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node node = childNodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        recordCount++;
                        Element element = (Element) node;
                        String recordId = element.getAttribute("id");
                        String name = element.getNodeName();
                        String value = element.getTextContent();
                        XmlParseData parseData = new XmlParseData();
                        parseData.setUploadId(upload.getId().longValue());
                        parseData.setRecordId(recordId);
                        parseData.setName(name);
                        parseData.setValue(value);
                        parseData.setDescription("Parsed from XML upload ID: " + upload.getId());
                        parseData.setUid(uid);
                        parseData.setCreatedAt(LocalDateTime.now());
                        parseData.setUpdatedAt(LocalDateTime.now());
                        parseData.setValid(true);
                        parseData.setStatus(Status.ACTIVE);
                        xmlParseDataRepository.save(parseData);
                    }
                }
                String schemaVersion = extractSchemaVersion(document);
                upload.setXmlSchemaVersion(schemaVersion);

            } else if (fileExtension.equals(".csv")) {
                // --- CSV Processing ---
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                    String line;
                    boolean isFirstLine = true;

                    while ((line = reader.readLine()) != null) {
                        if (isFirstLine) {
                            isFirstLine = false; // skip header
                            continue;
                        }
                        if (!line.trim().isEmpty()) {
                            recordCount++;
                        }
                    }
                }
                upload.setXmlSchemaVersion(" "); // Optional: you can leave blank or specify "CSV"
            }
            upload.setRecordCount(recordCount);
            upload.setFilePath(localFile.getAbsolutePath());
            upload.onCreate();
        } catch (Exception e) {
            success = false;
            errorMsg = "Error processing file: " + e.getMessage();
            upload.setValid(false);
            upload.setErrorMessage(errorMsg);
        }
        Instant endTime = Instant.now();
        //For storing the Processing time in seconds
        double processingTimeSec = Duration.between(startTime, endTime).toMillis() / 1000.0;
        upload.setProcessingTime(processingTimeSec);

        logXmlUpload(upload, success, errorMsg);
        xmlUploadRepository.updateFilePathAndProcessingTimeById(upload.getId(), upload.getFilePath(), upload.getProcessingTime());
        return xmlUploadRepository.save(upload);
    }

    private String extractSchemaVersion(Document document) {
        try {
            Element root = document.getDocumentElement();
            String schemaLocation = root.getAttribute("xsi:schemaLocation");

            if (schemaLocation != null && !schemaLocation.isEmpty()) {
                String[] parts = schemaLocation.split(" ");
                if (parts.length > 1) {
                    String schemaFile = parts[1]; // Get "product_schema_v2.0.xsd"
                    return schemaFile.replaceAll("[^0-9.]", ""); // Extract "2.0"
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting schema version: " + e.getMessage());
        }
        return "N/A";
    }

    //Storing logs for all XML upload operations
    public void logXmlUpload(XmlUpload upload, boolean success, String errorMsg) {
        XmlUploadLog log = new XmlUploadLog();
        log.setFileName(upload.getFileName());
        log.setFilePath(upload.getFilePath());
        log.setValid(success);
        log.setErrorMessage(success ? null : errorMsg);
        log.setUploadedAt(LocalDateTime.now());
        log.setUid(upload.getUid());
        log.setIpAddress(upload.getIpAddress());
        log.setPathId(upload.getPathId());
        log.setStatus(Status.ACTIVE);
        xmlUploadLogRepository.save(log);
    }

    //Implement a log cleanup mechanism for old records.
    @Transactional
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupOldLogs() {
        LocalDateTime retentionDate = LocalDateTime.now().minusDays(30);
        int deletedRecords = xmlUploadLogRepository.deleteByUploadedAtBefore(retentionDate);
        System.out.println(deletedRecords + " old log records deleted.");
    }

    public XmlUpload fetchUploadById(Integer id) {
        return xmlUploadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File with ID " + id + " not found"));
    }

    public String getFilePathById(Integer id) {
        XmlUpload xmlUpload = xmlUploadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
        return xmlUpload.getFilePath();
    }

    @Override
    public List<XmlUploadData> fetchAllUploads() {
        Map<String, Object> parameters = new HashMap<>();
        final XmlUploadRowMapper rowMapper = new XmlUploadRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE a.valid = '1' AND a.uid = b.uid AND a.pathId = c.id ORDER BY a.created_at DESC;";
        Qry = Qry + whereClause;
        final List<XmlUploadData> xmlUploadData = jdbcTemplate.query(Qry, new Object[]{}, xmlUploadRowMapper);
        return xmlUploadData;
    }

}
