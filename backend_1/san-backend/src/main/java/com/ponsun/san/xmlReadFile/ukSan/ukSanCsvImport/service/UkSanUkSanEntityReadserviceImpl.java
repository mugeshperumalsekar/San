package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.service;


import com.ponsun.san.common.entity.Status;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanEntityClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanEntityClassRepository;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsClass;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity.UkSanLogsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UkSanUkSanEntityReadserviceImpl implements UkSanEntityReadservice {
    private final UkSanEntityClassRepository entityClassRepository;
    private final UkSanLogsRepository ukSanLogsRepository;

    @Override
    public List<UkSanEntityClass> fetchAllentity() {
        return this.entityClassRepository.findByStatus(Status.ACTIVE);
    }

    @Override
    public List<UkSanLogsClass> getLogsByStatus() {
        return this.ukSanLogsRepository.findByStatus(Status.ACTIVE);
    }
}
