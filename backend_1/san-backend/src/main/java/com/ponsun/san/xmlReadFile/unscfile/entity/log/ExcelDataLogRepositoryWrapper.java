package com.ponsun.san.xmlReadFile.unscfile.entity.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelDataLogRepositoryWrapper {

    private final ExcelDataLogRepository excelDataLogRepository;

    @Transactional(readOnly = true)
    public ExcelDataLog findLatestOne() {
        return this.excelDataLogRepository.findLatestOne().orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ExcelDataLog> getAllExcelDataLog() {
        return this.excelDataLogRepository.findAll();
    }

}
