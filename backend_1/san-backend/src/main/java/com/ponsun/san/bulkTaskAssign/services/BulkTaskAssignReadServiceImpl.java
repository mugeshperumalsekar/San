package com.ponsun.san.bulkTaskAssign.services;


import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssign;
import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssignRepository;
import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssignWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkTaskAssignReadServiceImpl implements BulkTaskAssignReadService {
    private final BulkTaskAssignRepository bulkTaskAssignRepository;
    private final JdbcTemplate jdbcTemplate;
    private final BulkTaskAssignWrapper bulkTaskAssignWrapper;

    @Override
    public BulkTaskAssign fetchBulkTaskAssignById(Integer id) {
        return this.bulkTaskAssignRepository.findById(id).get();
    }

    @Override
    public List<BulkTaskAssign> fetchAllBulkTaskAssign() {
        return this.bulkTaskAssignRepository.findAll();
    }
}
