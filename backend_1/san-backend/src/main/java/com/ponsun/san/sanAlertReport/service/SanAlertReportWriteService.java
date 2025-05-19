package com.ponsun.san.sanAlertReport.service;

import com.ponsun.san.sanAlertReport.data.SanAlertReportData;

import java.util.List;

public interface SanAlertReportWriteService {
   List<SanAlertReportData> fetchAllLevelFlow(List<Integer> uid, List<Integer> statusId, String startDate, String endDate);

   List<SanAlertReportData> fetchSearchResults(
           List<Integer> uid, List<Integer> statusId, String startMonth, String startYear, String endMonth, String endYear);
}