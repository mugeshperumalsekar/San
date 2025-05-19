package com.ponsun.san.sanAlertReport.api;

import com.ponsun.san.sanAlertReport.data.SanAlertReportData;
import com.ponsun.san.sanAlertReport.service.SanAlertReportWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/SanAlertReport")
public class SanAlertReport {

    private final SanAlertReportWriteService sanAlertReportWriteService;

@GetMapping
public List<SanAlertReportData> fetchAll(
        @RequestParam List<Integer> uid,  // Accepting multiple uid values
        @RequestParam List<Integer> statusId,  // Accepting multiple statusId values
        @RequestParam String startDate,
        @RequestParam String endDate) {

    return this.sanAlertReportWriteService.fetchAllLevelFlow(uid, statusId, startDate, endDate);
}


    @GetMapping("/results")
    public List<SanAlertReportData> fetchSearchResults(
            @RequestParam List<Integer> uid,
            @RequestParam List<Integer> statusId,
            @RequestParam String startMonth,
            @RequestParam String startYear,
            @RequestParam String endMonth,
            @RequestParam String endYear) {

        return this.sanAlertReportWriteService.fetchSearchResults(uid, statusId, startMonth, startYear, endMonth, endYear);
    }
}
