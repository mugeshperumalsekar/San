package com.ponsun.san.xmlReadFile.unscfile.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.unscfile.domain.*;
import com.ponsun.san.xmlReadFile.unscfile.unexpectedcsv.CsvToDatabaseService;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLog;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLogRepository;
import com.ponsun.san.xmlReadFile.unscfile.entity.log.ExcelDataLogRepositoryWrapper;
import com.ponsun.san.xmlReadFile.unscfile.fileupload.XlsFileParser;
import com.ponsun.san.xmlReadFile.unscfile.request.CreateExcelDataLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.io.impl.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndividualCommandServiceImpl implements IndividualCommandService{

    private final IndividualRepositoryWrapper individualRepositoryWrapper;
    private final IndividualRepository individualRepository;
    private final ExcelDataLogRepositoryWrapper excelDataLogRepositoryWrapper;
    private final ExcelDataLogRepository excelDataLogRepository;
    private final JdbcTemplate jdbcTemplate;
    private final CsvToDatabaseService csvToDatabaseService;

    private boolean ensureColumns(List<String> csvHeaders) {
        Set<String> existingColumns = getExistingTableColumns("unsc_individuals");

        for (String header : csvHeaders) {
            if (!existingColumns.contains(header.toLowerCase())) {
                return false; // If even one header is missing, return false
            }
        }
        return true; // All headers are present
    }

    @Transactional
    @Override
    public Response saveCsvData(String filePath, String fileName) throws SQLException, IOException {
        Response response = new Response(0,"");
        log.debug("Initializing XlsFileParser with path: {} and filename: {}", filePath, fileName);

        XlsFileParser xlsFileParser = new XlsFileParser(filePath, fileName);
        List<String> fileHeaders = xlsFileParser.getAllHeaders();

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
                if(!ensureColumns(fileHeaders)){
//                    File file = new File(filePath, fileName);
//                    csvToDatabaseService.processCsvFile(file);
                    return new Response(0, "Unexpected csv data Found ! ");
//                        throw new ApplicationException("Column size mismatch");
                }

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
    public Response saveBulkData(List<Map<String, Object>> rows, CreateExcelDataLogRequest createExcelDataLogRequest) {
        try {
            Integer totalRecordSaved = 0;
            Integer totalRecordsUpdated = 0;
            long startTime = System.currentTimeMillis();

            log.info("Starting bulk data save process. Total rows received: {}", rows.size());
            List<Individual> individuals = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                String dataId = getString(row, "DATAID");
                log.debug("Processing row with DATAID: {}", dataId);

                List<String> newUpdateds = getLastUpdatedDates(row);
                Individual individualEntity = individualRepositoryWrapper.findOneWithDataId(dataId);

                if (individualEntity != null) {
                    List<LastUpdate> oldUpdates = individualEntity.getLastUpdates();
                    log.debug("Existing entity found for DATAID: {}. Old updates count: {}, New updates count: {}", dataId, oldUpdates.size(), newUpdateds.size());

                    if (newUpdateds.size() == oldUpdates.size()) {
                        log.debug("Skipping DATAID: {} as no updates are required.", dataId);
                        continue;
                    }

                    if (newUpdateds.size() > oldUpdates.size()) {
                        individualRepository.save(Individual.deactivateIndividual(individualEntity));
                        log.info("Entity class {} updated!", individualEntity.getId());
                        totalRecordsUpdated++;
                    }
                }

                log.debug("Creating new entities for DATAID: {}", dataId);
                Individual individual = Individual.create(row);

                individual.setTitles(Title.from(row,individual));
                individual.setDesignations(Designation.from(row,individual));
                individual.setNationalities(Nationality.from(row,individual));
                individual.setIndividualAliases(IndividualAlias.from(row,individual));
                individual.setAddresses(Address.from(row,individual));
                individual.setPlacesOfBirth(PlaceOfBirth.from(row,individual));
                individual.setDatesOfBirth(DateOfBirth.from(row,individual));
                individual.setDocuments(Document.from(row,individual));
                individual.setLastUpdates(LastUpdate.from(row,individual));

                individuals.add(individual);
                totalRecordSaved++;
                log.debug("Finished processing DATAID: {}. Total records saved so far: {}", dataId, totalRecordSaved);
            }
            if(!individuals.isEmpty()){
                this.individualRepository.saveAllAndFlush(individuals);
            }

            log.info("Saving entities to the database...");

            long endTime = System.currentTimeMillis();
            log.info("Bulk save process completed. Total records saved: {}, Total records updated: {}, Time taken: {} ms", totalRecordSaved, totalRecordsUpdated, (endTime - startTime));
            createExcelDataLogRequest.setUpdatedCount(totalRecordsUpdated);
            createExcelDataLogRequest.setTotalSavedCount(totalRecordSaved);
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

    public static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }

    private void deActivateExcelLogData(ExcelDataLog excelDataLog) {
        log.debug("Deactivating ExcelDataLog with ID: {}", excelDataLog.getId());
        excelDataLog.setStatus(Status.DELETE);
        excelDataLog.setUpdatedAt(LocalDateTime.now());
        excelDataLogRepository.save(excelDataLog);
        log.debug("ExcelDataLog deactivated and saved.");
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
    public Response excelcolumescheck(List<String> fileHeaders) {
        if (fileHeaders == null || fileHeaders.isEmpty()) {
            log.warn("CSV data is empty!");
            throw new IllegalArgumentException("CSV file is empty or unreadable.");
        }

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
//            throw new RuntimeException("Excel headers mismatch! Missing: " + missingFields + ", Extra: " + extraFields);
        }

        log.info("✅ Excel columns match expected headers.");
        return new Response();
    }

    private Set<String> getExistingTableColumns(String tableName) {
        Set<String> columns = new HashSet<>();
        String query = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name = ?";
        jdbcTemplate.query(query, new Object[]{tableName}, rs -> {
            try {
                columns.add(rs.getString("COLUMN_NAME").toLowerCase());
            } catch (SQLException e) {
                log.error("❌ Error reading column names: {}", e.getMessage());
            }
        });
        return columns;
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
