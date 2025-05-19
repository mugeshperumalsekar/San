package com.ponsun.san.xmlReadFile.euSan.euFile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EUCsvToDatabaseService {
    @Value("${app.csv.file.path.eu}")
    private String DIRECTORY_PATH;

    @Value("${app.csv.processed.file.eu}")
    private String PROCESSED_DIRECTORY_PATH;
    private static final String TABLE_NAME = "eu_csv_file";

    private final JdbcTemplate jdbcTemplate;
    private final EULogTable EULogTable;

    @Transactional
    public void processAllCsvFiles() throws IOException, SQLException {
        File directory = new File(DIRECTORY_PATH);
        File[] csvFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv") && !name.matches(".*-\\d{8}_\\d{6}\\.csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            log.warn("⚠ No new CSV files found in directory: {}", DIRECTORY_PATH);
            return;
        }

        Arrays.sort(csvFiles, Comparator.comparingLong(File::lastModified));
        if (csvFiles.length > 0) {

            File file = csvFiles[0];

            try {
                processCsvFile(file);
                renameProcessedFile(file);
            } catch (Exception e) {
                log.error("❌ Error processing file {}: {}", file.getName(), e.getMessage(), e);
            }
        }
    }
    public void renameProcessedFile(File originalFile) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String baseName = originalFile.getName().replaceFirst("(?i)\\.csv$", "");
        String newName = baseName + "-" + timestamp + ".csv";

        File processedDir = new File(PROCESSED_DIRECTORY_PATH);

        if (!processedDir.exists()) {
            processedDir.mkdirs();
        }

        File renamedFile = new File(processedDir, newName);

        try {
            Files.move(originalFile.toPath(), renamedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            log.info("📁 Moved and renamed file to: {}", renamedFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("❌ Failed to move file: {}", originalFile.getName(), e);
        }
    }

    @Transactional
    public void processCsvFile(File file) throws IOException, SQLException {
        log.info("✅ Processing file: {}", file.getName());

        LocalDateTime startTimeStamp = LocalDateTime.now();
        long startTime = System.currentTimeMillis();

        int totalRecords = 0, updatedRecords = 0, newRecords = 0, newColumns = 0;
        List<Map<String, String>> recordsToInsert = new ArrayList<>();

        List<String> headers;
        Map<String, String> existingEntityMap;

        EULogTable.createLogTableIfNotExists();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine == null || headerLine.trim().isEmpty()) {
                throw new IllegalStateException("CSV is empty.");
            }

            headers = Arrays.stream(headerLine.split(",")).map(String::trim).collect(Collectors.toList());
            if (!headers.contains("Status")) headers.add("Status");
            if (!headers.contains("created_at")) headers.add("created_at");
            if (!headers.contains("updated_at")) headers.add("updated_at");

            if (!tableExists()) {
                createTable(headers);
            }
            newColumns = ensureColumns(headers);
            existingEntityMap = loadExistingEntities();

            Map<String, String> deactivateMap = new HashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                totalRecords++;
                String[] values = line.split(",", -1);
                Map<String, String> record = new HashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    if (!List.of("Status", "created_at", "updated_at").contains(headers.get(i))) {
                        record.put(headers.get(i), i < values.length ? values[i] : "");
                    }
                }

                String entityLogicalId = record.getOrDefault("Entity_LogicalId", "");
                String fileGenerationDate = record.getOrDefault("fileGenerationDate", "");

                if (!StringUtils.hasText(entityLogicalId) || !StringUtils.hasText(fileGenerationDate)) {
                    continue;
                }

                String existingDate = existingEntityMap.get(entityLogicalId);

                if (existingDate == null) {
                    newRecords++;
                    record.put("Status", "A");
                    record.put("created_at", LocalDateTime.now().toString());
                    record.put("updated_at", LocalDateTime.now().toString());
                    recordsToInsert.add(record);
                } else if (!existingDate.equals(fileGenerationDate)) {
                    updatedRecords++;
                    deactivateMap.put(entityLogicalId, LocalDateTime.now().toString());
                    record.put("Status", "A");
                    record.put("created_at", LocalDateTime.now().toString());
                    record.put("updated_at", LocalDateTime.now().toString());
                    recordsToInsert.add(record);
                }
            }

            if (!deactivateMap.isEmpty()) {
                batchDeactivateOldRecords(deactivateMap);
            }

            if (!recordsToInsert.isEmpty()) {
                insertData(recordsToInsert, headers);
            }

        } catch (IOException e) {
            log.error("Error reading CSV file: {}", e.getMessage());
            throw e;
        }

        LocalDateTime endTimeStamp = LocalDateTime.now();
        long endTime = System.currentTimeMillis();
        long timeTakenMillis = endTime - startTime;
        String formatted = EULogTable.formatElapsedTime(timeTakenMillis);

        log.info("\n=== Summary ===");
        log.info("Start Time       : {}", startTimeStamp);
        log.info("✔ Total Processed: {}", totalRecords);
        log.info("✔ New Records    : {}", newRecords);
        log.info("✔ Updated Records: {}", updatedRecords);
        log.info("🆕 New Columns    : {}", newColumns);
        log.info("End Time         : {}", endTimeStamp);
        log.info("🕒 Time Taken     : {}", formatted);

        EULogTable.insertLogRecord(totalRecords, newRecords, updatedRecords, newColumns, timeTakenMillis);

    }

    private Map<String, String> loadExistingEntities() {
        Map<String, String> map = new HashMap<>();
        if (!tableExists()) return map;
        String sql = "SELECT Entity_LogicalId, fileGenerationDate FROM " + TABLE_NAME + " WHERE Status = 'A'";
        jdbcTemplate.query(sql, rs -> {
            try {
                map.put(rs.getString("Entity_LogicalId"), rs.getString("fileGenerationDate"));
            } catch (SQLException e) {
                log.error("❌ Error reading existing entity data: {}", e.getMessage());
            }
        });
        return map;
    }

    private boolean tableExists() {
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{TABLE_NAME}, Integer.class);
        return count != null && count > 0;
    }

    private void createTable(List<String> csvHeaders) {
        StringBuilder sql = new StringBuilder("CREATE TABLE `" + TABLE_NAME + "` (id INT AUTO_INCREMENT PRIMARY KEY");
        for (String header : csvHeaders) {
            sql.append(", `").append(header).append("` TEXT");
        }
        sql.append(")");
        try {
            jdbcTemplate.execute(sql.toString());
            log.info("✅ Table `{}` created successfully", TABLE_NAME);
        } catch (Exception e) {
            log.error("❌ Error creating table `{}`: {}", TABLE_NAME, e.getMessage());
        }
    }

    private int ensureColumns(List<String> csvHeaders) {
        Set<String> existingColumns = getExistingTableColumns();
        int newColumns = 0;

        for (String header : csvHeaders) {
            if (!existingColumns.contains(header.toLowerCase())) {
                String sql = "ALTER TABLE `" + TABLE_NAME + "` ADD COLUMN `" + header + "` TEXT";
                try {
                    jdbcTemplate.execute(sql);
                    newColumns++;
                    log.info("✅ Added missing column: {}", header);
                } catch (Exception e) {
                    log.error("❌ Error adding column '{}': {}", header, e.getMessage());
                }
            }
        }
        return newColumns;
    }

    private Set<String> getExistingTableColumns() {
        Set<String> columns = new HashSet<>();
        String query = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name = ?";
        jdbcTemplate.query(query, new Object[]{TABLE_NAME}, rs -> {
            try {
                columns.add(rs.getString("COLUMN_NAME").toLowerCase());
            } catch (SQLException e) {
                log.error("❌ Error reading column names: {}", e.getMessage());
            }
        });
        return columns;
    }

    private void insertData(List<Map<String, String>> records, List<String> headers) {
        if (records.isEmpty()) {
            log.warn("⚠ No data to insert.");
            return;
        }

        StringBuilder sql = new StringBuilder("INSERT INTO `" + TABLE_NAME + "` (");
        sql.append(String.join(", ", headers.stream().map(h -> "`" + h + "`").collect(Collectors.toList())));
        sql.append(") VALUES ");

        List<String> valueSets = new ArrayList<>();
        for (Map<String, String> record : records) {
            String values = headers.stream().map(header -> "'" + sanitizeValue(record.getOrDefault(header, "")) + "'").collect(Collectors.joining(", "));
            valueSets.add("(" + values + ")");
        }

        sql.append(String.join(", ", valueSets));

        try {
            jdbcTemplate.execute(sql.toString());
            log.info("✅ Inserted {} records successfully", records.size());
        } catch (Exception e) {
            log.error("❌ Error inserting data: {}", e.getMessage());
        }
    }

    private void batchDeactivateOldRecords(Map<String, String> deactivateMap) {
        String sql = "UPDATE " + TABLE_NAME + " SET Status = 'D', updated_at = ? WHERE Entity_LogicalId = ?";
        List<Object[]> batchArgs = new ArrayList<>();
        deactivateMap.forEach((id, updatedAt) -> batchArgs.add(new Object[]{updatedAt, id}));
        jdbcTemplate.batchUpdate(sql, batchArgs);
        log.info("✅ Deactivated {} old records", deactivateMap.size());
    }

    private String sanitizeValue(String value) {
        return value == null ? "" : value.replace("'", "''");
    }

}