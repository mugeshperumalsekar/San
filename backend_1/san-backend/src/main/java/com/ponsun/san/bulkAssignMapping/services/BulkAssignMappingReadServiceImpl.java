package com.ponsun.san.bulkAssignMapping.services;


import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMapping;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMappingRepository;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMappingWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkAssignMappingReadServiceImpl implements BulkAssignMappingReadService {
    private final BulkAssignMappingRepository bulkAssignMappingRepository;
    private final JdbcTemplate jdbcTemplate;
    private final BulkAssignMappingWrapper BulkAssignMappingWrapper;

    @Override
    public BulkAssignMapping fetchBulkAssignMappingById(Integer id) {
        return this.bulkAssignMappingRepository.findById(id).get();
    }

    @Override
    public List<BulkAssignMapping> fetchAllBulkAssignMapping() {
        return this.bulkAssignMappingRepository.findAll();
    }
}
