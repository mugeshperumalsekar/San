package com.ponsun.san.master.statusMapping.services;

import com.ponsun.san.master.statusMapping.domain.StatusMapping;

import java.util.List;

public interface StatusMappingReadService {
    List<StatusMapping> fetchAllStatusMapping();

    StatusMapping fetchStatusMappingById(Integer id);


    List<StatusMapping> fetchAllActive();
}
