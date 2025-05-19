package com.ponsun.san.master.level.services;

import com.ponsun.san.master.level.domain.Level;

import java.util.List;

public interface LevelReadService {
    List<Level> fetchAllLevel();

    Level fetchLevelById(Integer id);
}
