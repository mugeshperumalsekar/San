package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;


import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.request.CreateLogRequest;
import org.springframework.transaction.annotation.Transactional;


public interface UkSanLogsService {
    @Transactional
    Response saveLog(CreateLogRequest createLogRequest);

//
}