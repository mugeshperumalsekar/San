package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;

import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsRepository;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.request.CreateLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UkSanUkSanLogsServiceImpl implements UkSanLogsService {

    private final UkSanLogsRepository ukSanLogsRepository;

    @Transactional
    @Override
    public Response saveLog(CreateLogRequest createLogRequest) {
        try {
            final UkSanLogsClass ukSanLogsClass = UkSanLogsClass.create(createLogRequest);
            this.ukSanLogsRepository.saveAndFlush(ukSanLogsClass);
            return new Response("Log saved successfully");
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

//    @Override
//    public List<UkSanLogsClass> getLogsByStatus() {
//        return this.ukSanLogsRepository.findByStatus(Status.ACTIVE);
//    }
}
