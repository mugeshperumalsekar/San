package com.ponsun.san.LevelFlow.service;

import com.ponsun.san.LevelFlow.data.LevelFlowWriteData;
import com.ponsun.san.infrastructure.utils.Response;

public interface LevelFlowDataWriteService {
    Response handleReview(LevelFlowWriteData levelFlowWriteData);

}
