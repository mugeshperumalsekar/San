package com.ponsun.san.xmlReadFile.ofacSan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OFACLogTable {
    private static final String LOG_TABLE_NAME = "ofac_csv_file_logs";
    private final JdbcTemplate jdbcTemplate;

    public void createLogTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS `" + LOG_TABLE_NAME + "` (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, total_processed INT, new_records INT, updated_records INT, new_columns INT, " +
                "status VARCHAR(10), created_at DATETIME, updated_at DATETIME, time_taken VARCHAR(50))";
        try {
            jdbcTemplate.execute(sql);
            log.info("✅ Log table `{}` checked/created successfully", LOG_TABLE_NAME);
        } catch (Exception e) {
            log.error("❌ Error creating log table: {}", e.getMessage());
        }
    }

    public void insertLogRecord(int total, int newRecs, int updatedRecs, int newCols, long timeTakenMillis) {

        String formattedTime = formatElapsedTime(timeTakenMillis);

        String sql = "INSERT INTO `" + LOG_TABLE_NAME + "` (total_processed, new_records, updated_records, new_columns, status, created_at, updated_at, time_taken) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, total, newRecs, updatedRecs, newCols, "A",
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), formattedTime);
            log.info("📝 Log record inserted successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to insert log record: {}", e.getMessage());
        }
    }

    public List<Map<String, Object>> getAllLogRecords() {
        String sql = "SELECT * FROM `" + LOG_TABLE_NAME + "` ORDER BY created_at DESC";
        try {
            List<Map<String, Object>> logs = jdbcTemplate.queryForList(sql);
            log.info("📄 Fetched {} log record(s).", logs.size());
            return logs;
        } catch (Exception e) {
            log.error("❌ Failed to fetch log records: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public static String formatElapsedTime(long millis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%d min %d sec", minutes, seconds);
    }

}
