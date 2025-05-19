package com.ponsun.san.master.levelMapping.services;


import com.ponsun.san.common.entity.Status;
import com.ponsun.san.master.levelMapping.domain.LevelMapping;
import com.ponsun.san.master.levelMapping.domain.LevelMappingRepository;
import com.ponsun.san.master.levelMapping.domain.LevelMappingWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelMappingReadServiceImpl implements LevelMappingReadService {
    private final LevelMappingRepository levelMappingRepository;
    private final JdbcTemplate jdbcTemplate;
    private final LevelMappingWrapper levelMappingRepositoryWrapper;

    @Override
    public LevelMapping fetchLevelMappingById(Integer id) {
        return this.levelMappingRepository.findById(id).get();
    }
    @Override
    public List<LevelMapping> fetchAllLevelMapping() {
        return this.levelMappingRepository.findAll();
    }

    @Override
    public List<LevelMapping> fetchAllActive() {
        return this.levelMappingRepository.findByStatus(Status.ACTIVE);
    }
}
