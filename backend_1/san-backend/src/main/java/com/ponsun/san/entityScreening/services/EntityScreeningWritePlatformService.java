package com.ponsun.san.entityScreening.services;

import com.ponsun.san.entityScreening.data.EntityScreeningData;

import java.util.List;

public interface EntityScreeningWritePlatformService {
    List<EntityScreeningData> fetchAllEntityScreening(Integer kycId);
}
