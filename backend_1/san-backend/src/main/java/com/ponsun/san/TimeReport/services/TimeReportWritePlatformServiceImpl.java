package com.ponsun.san.TimeReport.services;

import com.ponsun.san.TimeReport.data.TimeReportData;
import com.ponsun.san.TimeReport.rowmapper.TimeReportRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeReportWritePlatformServiceImpl implements TimeReportWritePlatformService {
    private final JdbcTemplate jdbcTemplate;
    private  final TimeReportRowMapper timeReportRowMapper;

    @Override
    public List<TimeReportData> fetchTimeReportData(Integer levelId,Integer statusId,Integer uid){
        Map<String,Object> parameters = new HashMap<>();
        final TimeReportRowMapper rowmapper = new TimeReportRowMapper();
        String Qry = "SELECT " + rowmapper.tableSchema();
        String whereClause ="WHERE l.level_id = ? AND l.statusId = ? AND l.uid = ? GROUP BY s.id, s.name, s.created_at ORDER BY s.created_at DESC";
        Qry = Qry + whereClause;
        final List<TimeReportData> timeReportData = jdbcTemplate.query(Qry, new Object[]{levelId,statusId,uid}, timeReportRowMapper);
        return timeReportData;
    }
}

