package com.ponsun.san.master.statusMapping.services;


import com.ponsun.san.common.entity.Status;
import com.ponsun.san.master.statusMapping.domain.StatusMapping;
import com.ponsun.san.master.statusMapping.domain.StatusMappingRepository;
import com.ponsun.san.master.statusMapping.domain.StatusMappingWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusMappingReadServiceImpl implements StatusMappingReadService {
    private final StatusMappingRepository statusMappingRepository;
    private final JdbcTemplate jdbcTemplate;
    private final StatusMappingWrapper statusMappingRepositoryWrapper;

    @Override
    public StatusMapping fetchStatusMappingById(Integer id) {
        return this.statusMappingRepository.findById(id).get();
    }
    @Override
    public List<StatusMapping> fetchAllStatusMapping() {
        return this.statusMappingRepository.findAll();
    }
    @Override
    public List<StatusMapping> fetchAllActive() {
        return this.statusMappingRepository.findByStatus(Status.ACTIVE);
    }

}
