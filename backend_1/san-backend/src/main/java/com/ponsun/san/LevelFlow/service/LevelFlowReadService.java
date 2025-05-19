package com.ponsun.san.LevelFlow.service;

import com.ponsun.san.LevelFlow.data.LevelFlowData;

import java.util.List;

public interface LevelFlowReadService {
    List<LevelFlowData> fetchAllLevelFlow(Integer levelId,Integer statusId);

}
