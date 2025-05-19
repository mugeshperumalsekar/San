package com.ponsun.san.TimeReport.api;

import com.ponsun.san.TimeReport.data.TimeReportData;
import com.ponsun.san.TimeReport.services.TimeReportWritePlatformService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/TimeReport")
@CrossOrigin(origins = "http://localhost:3000")
public class TimeReportApiResources {

    private final TimeReportWritePlatformService timeReportWritePlatformService;

    public TimeReportApiResources(TimeReportWritePlatformService timeReportWritePlatformService){
        this.timeReportWritePlatformService = timeReportWritePlatformService;
    }
    @GetMapping
    public List<TimeReportData> fetchAll(@RequestParam Integer levelId, Integer statusId,Integer uid) {
        return this.timeReportWritePlatformService.fetchTimeReportData(levelId, statusId,uid);
    }
}
