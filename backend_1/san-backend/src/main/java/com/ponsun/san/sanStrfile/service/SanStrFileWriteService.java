package com.ponsun.san.sanStrfile.service;

import com.ponsun.san.sanStrfile.data.SanStrFileData;

import java.util.List;

public interface SanStrFileWriteService {
//    List<SanStrFileData> fetchAllLStr();

    List<SanStrFileData> fetchAllLStr(String startDate, String endDate);
}
