package com.ponsun.san.master.level.services;


import com.ponsun.san.master.level.domain.Level;
import com.ponsun.san.master.level.domain.LevelRepository;
import com.ponsun.san.master.level.domain.LevelWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelReadServiceImpl implements LevelReadService {
    private final LevelRepository levelRepository;
    private final JdbcTemplate jdbcTemplate;
    private final LevelWrapper LevelRepositoryWrapper;

    @Override
    public Level fetchLevelById(Integer id) {
        return this.levelRepository.findById(id).get();
    }
    @Override
    public List<Level> fetchAllLevel() {
        return this.levelRepository.findAll();
    }
}
