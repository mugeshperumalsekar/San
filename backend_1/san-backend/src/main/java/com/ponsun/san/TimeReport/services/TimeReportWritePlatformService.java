package com.ponsun.san.TimeReport.services;

import com.ponsun.san.TimeReport.data.TimeReportData;

import java.util.List;

public interface TimeReportWritePlatformService {
    List<TimeReportData> fetchTimeReportData(Integer levelId, Integer statusId,Integer uid);

}
