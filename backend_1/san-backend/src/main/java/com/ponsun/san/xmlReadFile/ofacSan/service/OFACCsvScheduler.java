package com.ponsun.san.xmlReadFile.ofacSan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class OFACCsvScheduler {

    private final OFACCsvToDatabaseService csvService;
    private final ReentrantLock lock = new ReentrantLock();

    @Scheduled(cron = "0 */5 * * * *")
    public void runCsvProcessingJob() {
        if (lock.tryLock()) {
            try {
                csvService.processAllCsvFiles();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("⏳ Previous job still running. Skipping this execution.");
        }
    }
}
