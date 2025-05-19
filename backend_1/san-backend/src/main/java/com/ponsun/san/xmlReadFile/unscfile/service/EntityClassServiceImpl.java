package com.ponsun.san.xmlReadFile.unscfile.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.unscfile.entity.dateupdate.DateUpdate;
import com.ponsun.san.xmlReadFile.unscfile.entity.dateupdate.DateUpdateRepository;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1.EntityClass1;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1.EntityClass1Repository;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1.EntityClass1RepositoryWrapper;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass2.EntityClass2;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass2.EntityClass2Repository;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass3.EntityClass3;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass3.EntityClass3Repository;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass4.EntityClass4;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass4.EntityClass4Repository;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLog;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLogRepository;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLogRepositoryWrapper;
import com.ponsun.san.xmlReadFile.unscfile.fileupload.XlsFileParser;
import com.ponsun.san.xmlReadFile.unscfile.request.CreateExcelDataLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.io.impl.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntityClassServiceImpl implements EntityClassService{
    
    private final EntityClass1Repository entityClass1Repository;
    private final EntityClass2Repository entityClass2Repository;
    private final EntityClass3Repository entityClass3Repository;
    private final EntityClass4Repository entityClass4Repository;
    private final DateUpdateRepository dateUpdateRepository;
    private final EntityClass1RepositoryWrapper entityClass1RepositoryWrapper;
    private final ExcelDataLogRepositoryWrapper excelDataLogRepositoryWrapper;
    private final ExcelDataLogRepository excelDataLogRepository;
    @Transactional
    @Override
    public Response saveCsvData(String filePath, String fileName) {
        Response response = new Response(0,"");
        log.debug("Initializing XlsFileParser with path: {} and filename: {}", filePath, fileName);

        XlsFileParser xlsFileParser = new XlsFileParser(filePath, fileName);
        List<String> fileHeaders = xlsFileParser.getAllHeaders();

        // Csv header checking
        excelcolumescheck(fileHeaders);

        ExcelDataLog oldExcelDataLog = excelDataLogRepositoryWrapper.findLatestOne();
        CreateExcelDataLogRequest createExcelDataLogRequest = new CreateExcelDataLogRequest();
        createExcelDataLogRequest.setSourceFileName(fileName);
        createExcelDataLogRequest.setFileHeaderCount(fileHeaders.size());

        log.debug("Extracted {} headers from the CSV file.", fileHeaders.size());

        if (oldExcelDataLog != null) {
            log.debug("Found previous ExcelDataLog with header count: {}", oldExcelDataLog.getFileHeaderCount());
            if (fileHeaders.size() != oldExcelDataLog.getFileHeaderCount()) {
                log.error("Column size mismatch: expected {} but found {}", oldExcelDataLog.getFileHeaderCount(), fileHeaders.size());
                throw new ApplicationException("Column size mismatch");
            }
        }

        List<Map<String, Object>> data = xlsFileParser.parseCsvData();
        createExcelDataLogRequest.setTotalRec(data.size());

        log.debug("Parsed {} records from CSV file.", data.size());

        response = saveBulkData(data, createExcelDataLogRequest);

        createExcelDataLogRequest.setNewtotalRec(data.size());

        if (oldExcelDataLog != null) {
            log.debug("Previous record count: {}", oldExcelDataLog.getTotalRec());
            createExcelDataLogRequest.setOldtotalRec(oldExcelDataLog.getTotalRec());
            createExcelDataLogRequest.setNewtotalRec(Math.max(data.size() - oldExcelDataLog.getTotalRec(), 0));

            log.debug("Deactivating old Excel log data.");
            this.deActivateExcelLogData(oldExcelDataLog);
        }

        log.debug("Saving new ExcelDataLog entry.");
        ExcelDataLog excelDataLog = ExcelDataLog.create(createExcelDataLogRequest);
        this.excelDataLogRepository.save(excelDataLog);

        log.debug("CSV data save process completed.");
        return response;
    }

    public Response excelcolumescheck(List<String> fileHeaders) {
        if (fileHeaders == null || fileHeaders.isEmpty()) {
            log.warn("CSV data is empty!");
//            throw new IllegalArgumentException("CSV file is empty or unreadable.");
        }

        // Simulating expected header list from first line (you had a map before, changed it to match List<String>)
        Set<String> csvColumns = fileHeaders.stream()
                .map(col -> col.replace("\uFEFF", "").trim())
                .collect(Collectors.toSet());

        Set<String> expectedColumns = loadExpectedColumnsFromJson("un_csv_columns.json");

        if (expectedColumns == null) {
            log.warn("Failed to load expected columns from JSON!");
        }

        Set<String> missingFields = new HashSet<>(expectedColumns);
        missingFields.removeAll(csvColumns);

        Set<String> extraFields = new HashSet<>(csvColumns);
        extraFields.removeAll(expectedColumns);

        if (!missingFields.isEmpty() || !extraFields.isEmpty()) {
            log.warn("❌ Missing Fields in Excel: {}", missingFields);
            log.warn("⚠ Extra Fields in Excel: {}", extraFields);

            throw new RuntimeException("Excel headers mismatch! Missing: " + missingFields + ", Extra: " + extraFields);
        }

        log.info("✅ Excel columns match expected headers.");
        return new Response();
    }

    private void deActivateExcelLogData(ExcelDataLog excelDataLog) {
        log.debug("Deactivating ExcelDataLog with ID: {}", excelDataLog.getId());
        excelDataLog.setStatus(Status.DELETE);
        excelDataLog.setUpdatedAt(LocalDateTime.now());
        excelDataLogRepository.save(excelDataLog);
        log.debug("ExcelDataLog deactivated and saved.");
    }


    public Response saveBulkData(List<Map<String, Object>> rows, CreateExcelDataLogRequest createExcelDataLogRequest) {
        try {
            Integer totalRecordSaved = 0;
            Integer totalRecordsUpdated = 0;
            long startTime = System.currentTimeMillis();

            log.info("Starting bulk data save process. Total rows received: {}", rows.size());

            List<EntityClass1> entityClass1List = new ArrayList<>();
            List<EntityClass2> entityClass2List = new ArrayList<>();
            List<EntityClass3> entityClass3List = new ArrayList<>();
            List<EntityClass4> entityClass4List = new ArrayList<>();

            for (Map<String, Object> row : rows) {
                String dataId = getString(row, "DATAID");
                log.debug("Processing row with DATAID: {}", dataId);

                List<String> newUpdateds = getLastUpdatedDates(row);
                EntityClass1 entityClass = entityClass1RepositoryWrapper.findOneWithDataId(dataId);

                if (entityClass != null) {
                    List<DateUpdate> oldUpdates = entityClass.getDateUpdated();
                    log.debug("Existing entity found for DATAID: {}. Old updates count: {}, New updates count: {}", dataId, oldUpdates.size(), newUpdateds.size());

                    if (newUpdateds.size() == oldUpdates.size()) {
                        log.debug("Skipping DATAID: {} as no updates are required.", dataId);
                        continue;
                    }

                    if (newUpdateds.size() > oldUpdates.size()) {
                        handleEntityClassRecord(entityClass);
                        log.info("Entity class {} updated!", entityClass.getId());
                        totalRecordsUpdated++;
                    }
                }

                log.debug("Creating new entities for DATAID: {}", dataId);
                EntityClass2 entityClass2 = EntityClass2.create(row);
                EntityClass3 entityClass3 = EntityClass3.create(row);
                EntityClass4 entityClass4 = EntityClass4.create(row);
                EntityClass1 entityClass1 = EntityClass1.create(row);

                List<DateUpdate> dateUpdates = this.createDataUpdates(newUpdateds, entityClass1);
                entityClass1.setDateUpdated(dateUpdates);

                entityClass1.setEntityClass2(entityClass2);
                entityClass1.setEntityClass3(entityClass3);
                entityClass1.setEntityClass4(entityClass4);

                entityClass2List.add(entityClass2);
                entityClass3List.add(entityClass3);
                entityClass4List.add(entityClass4);
                entityClass1List.add(entityClass1);

                totalRecordSaved++;
                log.debug("Finished processing DATAID: {}. Total records saved so far: {}", dataId, totalRecordSaved);
            }

            log.info("Saving entities to the database...");

            if (!entityClass2List.isEmpty()) {
                log.debug("Saving {} EntityClass2 records...", entityClass2List.size());
                entityClass2Repository.saveAllAndFlush(entityClass2List);
            }

            if (!entityClass3List.isEmpty()) {
                log.debug("Saving {} EntityClass3 records...", entityClass3List.size());
                entityClass3Repository.saveAllAndFlush(entityClass3List);
            }

            if (!entityClass4List.isEmpty()) {
                log.debug("Saving {} EntityClass4 records...", entityClass4List.size());
                entityClass4Repository.saveAllAndFlush(entityClass4List);
            }

            if (!entityClass1List.isEmpty()) {
                log.debug("Saving {} EntityClass1 records...", entityClass1List.size());
                entityClass1Repository.saveAllAndFlush(entityClass1List);
            }

            long endTime = System.currentTimeMillis();
            log.info("Bulk save process completed. Total records saved: {}, Total records updated: {}, Time taken: {} ms", totalRecordSaved, totalRecordsUpdated, (endTime - startTime));
            createExcelDataLogRequest.setUpdatedCount(totalRecordsUpdated);
            createExcelDataLogRequest.setTotalSavedCount(entityClass1List.size());
            createExcelDataLogRequest.setProcessingTimeMs((endTime - startTime));
            createExcelDataLogRequest.setRemarks("Bulk save process completed. Total records saved: "+totalRecordSaved+" Total records updated: "+totalRecordsUpdated+"Time taken: "+(endTime - startTime)+" ms");
            if(totalRecordSaved != 0){
                createExcelDataLogRequest.setOperationType("Created");
            }
            if(totalRecordsUpdated != 0){
                createExcelDataLogRequest.setOperationType("Updated");
            }
            if(totalRecordSaved != 0 && totalRecordsUpdated != 0 ){
                createExcelDataLogRequest.setOperationType("Created and Updated");
            }

            return new Response(totalRecordSaved , "Records Created Successfully !");

        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving bulk data: {}", e.getMessage(), e);
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while saving bulk data: {}", e.getMessage(), e);
            throw new ApplicationException(e.getMessage());
        }
    }

    private void handleEntityClassRecord(EntityClass1 entityClass1) {
        log.info("Handling entity record for EntityClass1 ID: {}", entityClass1.getId());

        if (entityClass1.getEntityClass2() != null) {
            EntityClass2 entityClass2 = entityClass1.getEntityClass2();
            log.debug("Marking EntityClass2 ID: {} as DELETED", entityClass2.getId());
            entityClass2.setStatus(Status.DELETE);
            entityClass2.setUpdatedAt(LocalDateTime.now());
            entityClass2Repository.save(entityClass2);
            log.info("EntityClass2 ID: {} updated with status DELETE", entityClass2.getId());
        } else {
            log.debug("EntityClass2 is NULL for EntityClass1 ID: {}", entityClass1.getId());
        }

        if (entityClass1.getEntityClass3() != null) {
            EntityClass3 entityClass3 = entityClass1.getEntityClass3();
            log.debug("Marking EntityClass3 ID: {} as DELETED", entityClass3.getId());
            entityClass3.setStatus(Status.DELETE);
            entityClass3.setUpdatedAt(LocalDateTime.now());
            entityClass3Repository.save(entityClass3);
            log.info("EntityClass3 ID: {} updated with status DELETE", entityClass3.getId());
        } else {
            log.debug("EntityClass3 is NULL for EntityClass1 ID: {}", entityClass1.getId());
        }

        if (entityClass1.getEntityClass4() != null) {
            EntityClass4 entityClass4 = entityClass1.getEntityClass4();
            log.debug("Marking EntityClass4 ID: {} as DELETED", entityClass4.getId());
            entityClass4.setStatus(Status.DELETE);
            entityClass4.setUpdatedAt(LocalDateTime.now());
            entityClass4Repository.save(entityClass4);
            log.info("EntityClass4 ID: {} updated with status DELETE", entityClass4.getId());
        } else {
            log.debug("EntityClass4 is NULL for EntityClass1 ID: {}", entityClass1.getId());
        }

        log.debug("Deactivating date updates for EntityClass1 ID: {}", entityClass1.getId());
        entityClass1.setDateUpdated(deactivateDateupdates(entityClass1.getDateUpdated()));

        log.debug("Marking EntityClass1 ID: {} as DELETED", entityClass1.getId());
        entityClass1.setStatus(Status.DELETE);
        entityClass1.setUpdatedAt(LocalDateTime.now());
        entityClass1Repository.save(entityClass1);
        log.info("EntityClass1 ID: {} updated with status DELETE", entityClass1.getId());
    }

    private String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        return (value != null) ? value.toString().trim() : "";
    }

    public List<String> getLastUpdatedDates(Map<String, Object> row) {
        return row.entrySet().stream()
                .filter(entry -> entry.getKey().matches("LAST_DAY_UPDATED/VALUE/\\d+"))
                .sorted(Comparator.comparingInt(entry -> extractIndex(entry.getKey())))
                .map(entry -> entry.getValue() != null ? entry.getValue().toString().trim() : null)
                .filter(value -> value != null && !value.isEmpty())
                .collect(Collectors.toList());
    }

    private int extractIndex(String key) {
        return Integer.parseInt(key.replaceAll("\\D+", ""));
    }

    private List<DateUpdate> createDataUpdates (List<String> newUpdateds, EntityClass1 entityClass1){
        return newUpdateds.stream()
                .map(date -> {
                    DateUpdate dateUpdate = new DateUpdate();
                    dateUpdate.setLastDayUpdated(date);
                    dateUpdate.setEntityClass1(entityClass1);
                    dateUpdate.setStatus(Status.ACTIVE);
                    dateUpdate.setCreatedAt(LocalDateTime.now());
                    return dateUpdate;
                })
                .collect(Collectors.toList());
    }

    private List<DateUpdate> deactivateDateupdates (List<DateUpdate> dbData){
        dbData.forEach(existing -> {
            existing.setStatus(Status.DELETE);
            existing.setUpdatedAt(LocalDateTime.now());
        });

        return dbData;
    }

    private List<DateUpdate> handleDataUpdates(List<DateUpdate> dbData, List<String> newUpdateds, EntityClass1 entityClass1) {
        // Convert existing database dates to a Set for quick lookup
        Set<String> existingDates = dbData.stream()
                .map(DateUpdate::getLastDayUpdated)
                .collect(Collectors.toSet());

        List<DateUpdate> updatedRecords = new ArrayList<>();
        List<DateUpdate> newRecords = new ArrayList<>();

        for (DateUpdate existing : dbData) {
            if (newUpdateds.contains(existing.getLastDayUpdated().toString())) {
                existing.setStatus(Status.DELETE);
                existing.setUpdatedAt(LocalDateTime.now());
            }
            updatedRecords.add(existing);
        }
        if(updatedRecords != null && !updatedRecords.isEmpty()){
            dateUpdateRepository.saveAll(updatedRecords);
        }
        for (String newDate : newUpdateds) {
            String date = newDate;
            if (!existingDates.contains(date)) {
                DateUpdate dateUpdate = new DateUpdate();
                dateUpdate.setLastDayUpdated(date);
                dateUpdate.setEntityClass1(entityClass1);
                dateUpdate.setStatus(Status.ACTIVE);
                dateUpdate.setCreatedAt(LocalDateTime.now());
                newRecords.add(dateUpdate);
            }
        }
        return newRecords;
    }

    @Override
    public Response saveBulkData(List<Map<String, Object>> rows) {
        return null;
    }

    private Set<String> loadExpectedColumnsFromJson(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = new ClassPathResource("logconfig/" + fileName).getInputStream()) {
            JsonNode node = mapper.readTree(input);
            JsonNode columnsArray = node.get("un_csv_columns");
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
}
