package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanEntityClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanEntityClassRepository;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsRepository;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.fileupload.XlsFileParser;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.request.CreateLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.io.impl.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UkSanUkSanEntityClassServiceImpl implements UkSanEntityClassService {

    private final UkSanEntityClassRepository entityClassRepository;
    private final UkSanLogsService ukSanLogsService;
    private final UkSanLogsRepository ukSanLogsRepository;

    public Response processCsvData(String filePath, String fileName) {
        XlsFileParser xlsFileParser = new XlsFileParser(filePath, fileName);
        final List<Map<String, Object>> rows = xlsFileParser.parseCsvData();
        if(excelColumnsCheck(rows,filePath+fileName,"System")){
            saveBulkData(rows, filePath+fileName,"System");
        }else{
            log.warn("Skipping bulk data save due to column mismatch.");
            throw new RuntimeException("Skipping bulk data save due to column mismatch.");
//            return new Response("Skipping bulk data save due to column mismatch.");
        }
        return null;
    }

    public boolean excelColumnsCheck(List<Map<String, Object>> rows, String sourceFileName, String performedBy) {
        if (rows == null || rows.isEmpty()) {
            log.warn("CSV data is empty!");
            return false;
        }

        Set<String> csvColumns = rows.get(0).keySet().stream()
                .map(key -> key.replace("\uFEFF", "").trim())
                .collect(Collectors.toSet());

        Set<String> expectedColumns = loadExpectedColumnsFromJson("csv_columns.json");

        if (expectedColumns == null) {
            log.warn("Failed to load expected columns from JSON!");
            return false;
        }

        Set<String> missingFields = expectedColumns.stream()
                .filter(col -> !csvColumns.contains(col))
                .collect(Collectors.toSet());

        Set<String> extraFields = csvColumns.stream()
                .filter(col -> !expectedColumns.contains(col))
                .collect(Collectors.toSet());

        if (!missingFields.isEmpty() || !extraFields.isEmpty()) {
            log.warn("❌ Missing Fields in CSV: {}", missingFields);
            log.warn("⚠ Extra Fields in CSV: {}", extraFields);
            return false;
        }

        return true;
    }

    @Transactional
    public Response saveBulkData(List<Map<String, Object>> rows, String sourceFileName, String performedBy) {
        long startTime = System.currentTimeMillis();
        LocalTime.now();
        try {
            long insertedCount = 0L;
            long updatedCount = 0L;
            long skippedCount = 0L;

            List<UkSanEntityClass> recordsToSave = new ArrayList<>();
            List<UkSanEntityClass> recordsToUpdate = new ArrayList<>();

            // Find all existing records by unique ID
            Map<String, List<UkSanEntityClass>> existingRecordsMap = rows.stream()
                    .map(row -> getString(row, "Unique ID"))
                    .filter(id -> !id.isEmpty())
                    .distinct()
                    .collect(Collectors.toMap(
                            id -> id,
                            id -> entityClassRepository.findByUniqueIdAndStatus(id, Status.ACTIVE)
                    ));

            List<UkSanEntityClass> oldtotalRecords = entityClassRepository.findByStatus(Status.ACTIVE);

            for (Map<String, Object> row : rows) {
                String uniqueId = getString(row, "Unique ID");
                String lastUpdated = getString(row, "Last Updated");

                if (uniqueId.isEmpty()) {
                    log.warn("Skipping row due to missing Unique ID.");
                    continue;
                }

                List<UkSanEntityClass> matchedRecords = existingRecordsMap.getOrDefault(uniqueId, List.of());

                // Insert new record if not exists
                if (matchedRecords.isEmpty()) {
                    recordsToSave.add(UkSanEntityClass.create(row));
                    insertedCount++;
                    log.info("New record created for Unique ID: {}", uniqueId);
                    continue;
                }

                // Skip if record already exists with same last updated
                boolean exists = matchedRecords.stream()
                        .anyMatch(record -> record.getLastUpdated().equals(lastUpdated));

                if (exists) {
                    skippedCount++;
                    log.info("Skipping insertion for Unique ID: {} as lastUpdated ({}) already exists.", uniqueId, lastUpdated);
                    continue;
                }
                // Mark existing record as deleted
                for (UkSanEntityClass existingRecord : matchedRecords) {
                    existingRecord.setStatus(Status.DELETE);
                    existingRecord.setUpdatedAt(LocalDateTime.now());
//                    entityClassRepository.save(existingRecord);
                    recordsToUpdate.add(existingRecord);
                    updatedCount++;
                    log.info("Marked existing record as DELETED for Unique ID: {}, Last Updated: {}", uniqueId, existingRecord.getLastUpdated());
                }

                recordsToSave.add(UkSanEntityClass.create(row));
                insertedCount++;
                log.info("New record created for Unique ID: {} with Last Updated: {}", uniqueId, lastUpdated);
            }
            entityClassRepository.saveAll(recordsToUpdate);
            entityClassRepository.saveAll(recordsToSave);

            List<UkSanEntityClass> totalRecords = entityClassRepository.findByStatus(Status.ACTIVE);

            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;

            String formattedTime = time(processingTime);

            ukSanLogsService.saveLog(buildSuccessLog(sourceFileName, performedBy,
                    String.valueOf(oldtotalRecords.size()),
                    String.valueOf(insertedCount),
                    String.valueOf(updatedCount),
                    String.valueOf(totalRecords.size()),
                    formattedTime,
                    String.valueOf(skippedCount)
            ));
            log.info("Successfully saved {} new records.", insertedCount);
//            return new Response(insertedCount);
            return null;

        } catch (DataIntegrityViolationException e) {
            log.error("DataIntegrityViolationException: {}", e.getMessage());

            long endTime = System.currentTimeMillis();
            long processingTimes = endTime - startTime;
            String formattedTimes = time(processingTimes);

            List<UkSanEntityClass> totalRecords = entityClassRepository.findByStatus(Status.ACTIVE);
            handleFailure(sourceFileName, performedBy, totalRecords.size(), e.getMessage(), formattedTimes);
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());

            long endTime = System.currentTimeMillis();
            long processingTimes = endTime - startTime;
            String formattedTimes = time(processingTimes);

            List<UkSanEntityClass> totalRecords = entityClassRepository.findByStatus(Status.ACTIVE);
            handleFailure(sourceFileName, performedBy, totalRecords.size(), e.getMessage(), formattedTimes);
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }
    private void handleFailure(String file, String user, int total, String reason, String duration) {
        CreateLogRequest createLogRequest = new CreateLogRequest();
        createLogRequest.setOldtotalRec("0");
        createLogRequest.setNewtotalRec("0");
        createLogRequest.setUpdatedCount("0");
        createLogRequest.setTotalRec(String.valueOf(total));
        createLogRequest.setOperationType("Bulk Insert");
        createLogRequest.setSourceFileName(file);
        createLogRequest.setPerformedBy(user);
        createLogRequest.setUploadStatus("FAILED");
        createLogRequest.setFailureReason(reason);
        createLogRequest.setProcessingTimeMs(duration);
        createLogRequest.setCreatedAt(LocalDateTime.now());
        ukSanLogsService.saveLog(createLogRequest);
    }
    private CreateLogRequest buildSuccessLog(String file, String user, String oldCount, String newCount, String updated, String total, String duration, String alreadyExists) {
        CreateLogRequest createLogRequest = new CreateLogRequest();
        createLogRequest.setOldtotalRec(oldCount);
        createLogRequest.setNewtotalRec(newCount);
        createLogRequest.setUpdatedCount(updated);
        createLogRequest.setTotalRec(String.valueOf(total));
        createLogRequest.setAlreadyExists(String.valueOf(alreadyExists));
        createLogRequest.setOperationType("Bulk Insert");
        createLogRequest.setSourceFileName(file);
        createLogRequest.setPerformedBy(user);
        createLogRequest.setUploadStatus("SUCCESS");
        createLogRequest.setFailureReason(null);
        createLogRequest.setProcessingTimeMs(duration);
        createLogRequest.setCreatedAt(LocalDateTime.now());
        return createLogRequest;
    }

    private String getString(Map<String, Object> row, String key) {
        String normalizedKey = key.replaceAll("[^\\x00-\\x7F]", "").trim();

        for (String actualKey : row.keySet()) {
            String cleanKey = actualKey.replaceAll("[^\\x00-\\x7F]", "").trim(); // Normalize actual keys too
            if (cleanKey.equalsIgnoreCase(normalizedKey)) {
                Object value = row.get(actualKey);
                return (value == null) ? "" : value.toString().trim();
            }
        }
        return "";
    }

    private Set<String> loadExpectedColumnsFromJson(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = new ClassPathResource("logconfig/" + fileName).getInputStream()) {
            JsonNode node = mapper.readTree(input);
            JsonNode columnsArray = node.get("csv_columns");

            if (columnsArray == null || !columnsArray.isArray()) return null;

            Set<String> expectedColumns = new HashSet<>();
            for (JsonNode column : columnsArray) {
                expectedColumns.add(column.asText().trim());
            }

            return expectedColumns;
        } catch (IOException e) {
            log.error("Error reading JSON file for expected columns: ", e);
            return null;
        }
    }

    private String time(long processingTimes) {
        long hours = processingTimes / (1000 * 60 * 60);
        long minutes = (processingTimes / (1000 * 60)) % 60;
        long seconds = (processingTimes / 1000) % 60;
        long milliseconds = processingTimes % 1000;
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
    }
}

//    @Transactional
//    public Response saveBulkData(List<Map<String, Object>> rows) {
//        try {
//            String totalRecordSaved = 0L;
//            List<UkSanEntityClass> recordsToSave = new ArrayList<>();
//
//            // Extract all unique IDs from input rows
//            List<String> uniqueIds = rows.stream()
//                    .map(row -> getString(row, "Unique ID"))
//                    .filter(id -> !id.isEmpty())
//                    .distinct()
//                    .toList();
//
//            Map<String, List<UkSanEntityClass>> existingRecordsMap = new HashMap<>();
//
//            for (String uniqueId : uniqueIds) {
//                List<UkSanEntityClass> records = entityClassRepository.findByUniqueId(uniqueId);
//                if (!records.isEmpty()) {
//                    existingRecordsMap.put(uniqueId, records);
//                    log.info("Fetched {} existing records for Unique ID: {}", records.size(), uniqueId);
//                } else {
//                    log.info("No existing records found for Unique ID: {}", uniqueId);
//                }
//            }
//
//            for (Map<String, Object> row : rows) {
//                String Id = getString(row, "Unique ID");
//                String lastUpdated = getString(row, "Last Updated");
//
//                if (Id.isEmpty()) {
//                    log.warn("Skipping row due to missing Unique ID.");
//                    continue;
//                }
//
//                List<UkSanEntityClass> matchedRecords = existingRecordsMap.getOrDefault(Id, List.of());
//
//                if (matchedRecords.isEmpty()) {
//                    UkSanEntityClass newRecord = UkSanEntityClass.create(row);
//                    recordsToSave.add(newRecord);
//                    totalRecordSaved++;
//                    log.info("New record created for Unique ID: {}", Id);
//                    continue;
//                }
//
//                // Check if lastUpdated already exists
//                boolean lastUpdatedExists = matchedRecords.stream().anyMatch(record -> record.getLastUpdated().equals(lastUpdated));
//
//                if (lastUpdatedExists) {
//                    log.info("Skipping insertion for Unique ID: {} as lastUpdated ({}) already exists.", Id, lastUpdated);
//                    continue;
//                }
//
//                // If lastUpdated is different → Mark old records as deleted and save a new record
//                for (UkSanEntityClass existingRecord : matchedRecords) {
//                    existingRecord.setStatus(Status.DELETE);
//                    existingRecord.setUpdatedAt(LocalDateTime.now());
//                    entityClassRepository.save(existingRecord);
//                    log.info("Marked existing record as DELETED for Unique ID: {}, Last Updated: {}", Id, existingRecord.getLastUpdated());
//                }
//
//                // Save new record
//                UkSanEntityClass newRecord = UkSanEntityClass.create(row);
//                recordsToSave.add(newRecord);
//                totalRecordSaved++;
//                log.info("New record created for Unique ID: {} with Last Updated: {}", Id, lastUpdated);
//            }
//            entityClassRepository.saveAll(recordsToSave);
//            log.info("Successfully saved {} new records.", totalRecordSaved);
//            return new Response(totalRecordSaved);
//        } catch (DataIntegrityViolationException e) {
//            log.error("Error while saving bulk data: {}", e.getMessage());
//            throw new EQAS_SAN_ApplicationException(e.getMessage());
//        } catch (Exception e) {
//            log.error("Unexpected error while saving bulk data: {}", e.getMessage());
//            throw new EQAS_SAN_ApplicationException(e.getMessage());
//        }
//    }