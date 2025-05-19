package com.ponsun.san.master.level.services;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.level.request.CreateLevelRequest;
import com.ponsun.san.master.level.request.UpdateLevelRequest;

public interface LevelWriteService {
    Response createLevel(CreateLevelRequest createLevelRequest);

    Response updateLevel(Integer id, UpdateLevelRequest updateLevelRequest);
}
