package com.ponsun.san.searchLifcycle.RemarkDetails.services;

import com.ponsun.san.searchLifcycle.RemarkDetails.data.RemarkDetailsData;

import java.util.List;

public interface RemarkDetailsReadPlatformService {
    List<RemarkDetailsData>fetchAllRemark(Integer hitdataId);
}
