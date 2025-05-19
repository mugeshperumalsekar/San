package com.ponsun.san.master.levelMapping.services;

import com.ponsun.san.master.levelMapping.domain.LevelMapping;

import java.util.List;

public interface LevelMappingReadService {
    List<LevelMapping> fetchAllLevelMapping();

    LevelMapping fetchLevelMappingById(Integer id);


    List<LevelMapping> fetchAllActive();
}
