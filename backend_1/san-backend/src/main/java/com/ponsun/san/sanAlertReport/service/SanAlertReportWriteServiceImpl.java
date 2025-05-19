package com.ponsun.san.sanAlertReport.service;

import com.ponsun.san.sanAlertReport.data.SanAlertReportData;
import com.ponsun.san.sanAlertReport.rowmapper.SanAlertReportRowmapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SanAlertReportWriteServiceImpl implements SanAlertReportWriteService {

    private final JdbcTemplate jdbcTemplate;
    private final SanAlertReportRowmapper sanAlertReportRowmapper;

    @Override
    public List<SanAlertReportData> fetchAllLevelFlow(List<Integer> uid, List<Integer> statusId, String startDate, String endDate) {
        // Generate the placeholders for IN clauses
        String uidPlaceholders = String.join(",", "?".repeat(uid.size()).split(""));
        String statusIdPlaceholders = String.join(",", "?".repeat(statusId.size()).split(""));

        // Define the SQL query
        String query = "SELECT " + sanAlertReportRowmapper.tableSchema() +
                " WHERE a.id = b.searchid " +
                " AND a.id = c.search_id " +
                " AND b.id = c.hitdata_id " +
                " AND c.level_id = d.LevelId " +
                " AND c.statusId = d.statusId " +
                " AND e.id = d.statusId " +
                " AND a.uid = f.id " +
                " AND a.uid IN (" + uidPlaceholders + ") " +  // Insert the correct number of placeholders for `uid`
                " AND d.statusId IN (" + statusIdPlaceholders + ") " +  // Insert the correct number of placeholders for `statusId`
                " AND DATE(a.created_at) BETWEEN ? AND ?";

        // Merge all parameters into one array
        List<Object> params = new ArrayList<>();
        params.addAll(uid);         // Add the `uid` list to the params
        params.addAll(statusId);    // Add the `statusId` list to the params
        params.add(startDate);      // Add `startDate`
        params.add(endDate);        // Add `endDate`

        // Execute the query using the JdbcTemplate and return the results
        return jdbcTemplate.query(query, params.toArray(), sanAlertReportRowmapper);
    }



    @Override
    public List<SanAlertReportData> fetchSearchResults(
            List<Integer> uid, List<Integer> statusId, String startMonth, String startYear, String endMonth, String endYear) {

        // Generate placeholders for IN clause
        String uidPlaceholders = String.join(",", "?".repeat(uid.size()).split(""));
        String statusIdPlaceholders = String.join(",", "?".repeat(statusId.size()).split(""));

        // Define the SQL query
        String query = "SELECT " + sanAlertReportRowmapper.tableSchema() +
                " WHERE a.id = b.searchid " +
                " AND a.id = c.search_id " +
                " AND b.id = c.hitdata_id " +
                " AND c.level_id = d.LevelId " +
                " AND c.statusId = d.statusId " +
                " AND e.id = d.statusId " +
                " AND a.uid = f.id " +
                " AND a.uid IN (" + uidPlaceholders + ") " +
                " AND d.statusId IN (" + statusIdPlaceholders + ") " +
                " AND DATE_FORMAT(a.created_at, '%Y-%m') BETWEEN ? AND ?";

        // Merge all parameters into one list
        List<Object> params = new ArrayList<>();
        params.addAll(uid);                   // Add user IDs
        params.addAll(statusId);              // Add status IDs
        params.add(startYear + "-" + startMonth);  // Convert start date to 'YYYY-MM'
        params.add(endYear + "-" + endMonth);      // Convert end date to 'YYYY-MM'

        // Execute the query using JdbcTemplate
        return jdbcTemplate.query(query, params.toArray(), sanAlertReportRowmapper);
    }


}
