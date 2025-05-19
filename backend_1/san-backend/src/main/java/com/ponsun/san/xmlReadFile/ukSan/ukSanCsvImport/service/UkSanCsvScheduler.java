package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;

import com.ponsun.san.infrastructure.utils.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class UkSanCsvScheduler {

    @Value("${app.csv.file.path.uk}")
    private String DIRECTORY_PATH;
    @Value("${app.csv.processed.file.un}")
    private String PROCESSED_DIRECTORY_PATH;

    private final UkSanUkSanEntityClassServiceImpl csvService;
    private final ReentrantLock lock = new ReentrantLock();

    @Scheduled(cron = "0 */5 * * * *")
    public void runCsvProcessingJob() {
        if (lock.tryLock()) {
            try {
                processAllCsvFiles();
//                csvService.processCsvData();
            } catch (Exception e) {
                e.printStackTrace(); // You can log it instead
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("⏳ Previous job still running. Skipping this execution.");
        }
    }

    public Response processAllCsvFiles() throws IOException, SQLException {
        File directory = new File(DIRECTORY_PATH);
        File[] csvFiles = directory.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".csv") && !name.matches(".*-\\d{8}_\\d{6}\\.csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            log.warn("⚠ No new CSV files found in directory: {}", DIRECTORY_PATH);
            return new Response(" No new CSV files found in directory");
        }

        Arrays.sort(csvFiles, Comparator.comparingLong(File::lastModified));
        Response response = null;
        if (csvFiles.length > 0) {

            File file = csvFiles[0];

            try {
                response = this.csvService.processCsvData(DIRECTORY_PATH, file.getName());
                renameProcessedFile(file);
            } catch (Exception e) {
                log.error("❌ Error processing file {}: {}", file.getName(), e.getMessage(), e);
            }
        }
        return response;
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

}

