package com.ponsun.san.master.LevelOne.service;

import com.ponsun.san.master.LevelOne.data.LevelOneData;
import com.ponsun.san.master.LevelOne.data.StatusDataMapping;

import java.util.List;

public interface LevelOneReadService {
    List<LevelOneData> fetchLevelOneData(Integer levelId);

    List<StatusDataMapping> fetchStatusMappingData(Integer levelId);
}
