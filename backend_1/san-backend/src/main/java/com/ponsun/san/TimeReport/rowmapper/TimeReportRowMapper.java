package com.ponsun.san.TimeReport.rowmapper;

import com.ponsun.san.TimeReport.data.TimeReportData;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class TimeReportRowMapper implements RowMapper<TimeReportData> {
    public final String schema;

    public TimeReportRowMapper(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM search s JOIN hitrecord h ON s.id = h.searchId LEFT JOIN hitrecord_lifecycle l ON h.id = l.hitdata_id ");
        this.schema = builder.toString();
    }

    public String schema(){ return this.schema; }

    public String tableSchema(){
        final StringBuilder builder = new StringBuilder(200);

        builder.append(" s.name AS searched_name, ");
        builder.append(" s.created_at AS search_inserted_time, ");
        builder.append(" SECOND(s.created_at) AS search_inserted_seconds, ");
        builder.append(" COUNT(h.id) AS total_related_records, ");
        builder.append(" GROUP_CONCAT(h.created_at ORDER BY h.created_at ASC SEPARATOR ', ') AS all_hitrecord_inserted_times, ");

        // Closed case
        builder.append(" MAX(CASE WHEN l.statusId = 1 THEN h.name END) AS closed_case_name, ");
        builder.append(" MIN(CASE WHEN l.statusId = 1 THEN l.created_at END) AS case_closed_time, ");
        builder.append(" SECOND(MIN(CASE WHEN l.statusId = 1 THEN l.created_at END)) AS case_closed_seconds, ");
        builder.append(" TIMESTAMPDIFF(SECOND, s.created_at, MIN(CASE WHEN l.statusId = 1 THEN l.created_at END)) AS time_to_close_seconds, ");
        builder.append(" MAX(CASE WHEN l.statusId = 1 THEN l.level_id END) AS closed_level_id, ");
        builder.append(" MAX(CASE WHEN l.statusId = 1 THEN l.case_id END) AS closed_case_id, ");
        builder.append(" MAX(CASE WHEN l.statusId = 1 THEN l.hitdata_id END) AS closed_hitdata_id, ");

        // Escalated case
        builder.append(" MAX(CASE WHEN l.statusId = 2 THEN h.name END) AS escalated_case_name, ");
        builder.append(" MIN(CASE WHEN l.statusId = 2 THEN l.created_at END) AS escalation_time, ");
        builder.append(" SECOND(MIN(CASE WHEN l.statusId = 2 THEN l.created_at END)) AS escalation_seconds, ");
        builder.append(" TIMESTAMPDIFF(SECOND, s.created_at, MIN(CASE WHEN l.statusId = 2 THEN l.created_at END)) AS time_to_escalation_seconds, ");
        builder.append(" MAX(CASE WHEN l.statusId = 2 THEN l.level_id END) AS escalated_level_id, ");
        builder.append(" MAX(CASE WHEN l.statusId = 2 THEN l.case_id END) AS escalated_case_id, ");
        builder.append(" MAX(CASE WHEN l.statusId = 2 THEN l.hitdata_id END) AS escalated_hitdata_id, ");

        // RFI case
        builder.append(" MAX(CASE WHEN l.statusId = 3 THEN h.name END) AS rfi_case_name, ");
        builder.append(" MIN(CASE WHEN l.statusId = 3 THEN l.created_at END) AS rfi_time, ");
        builder.append(" TIMESTAMPDIFF(SECOND, s.created_at, MIN(CASE WHEN l.statusId = 3 THEN l.created_at END)) AS time_to_rfi_seconds, ");
        builder.append(" MAX(CASE WHEN l.statusId = 3 THEN l.level_id END) AS rfi_level_id, ");
        builder.append(" MAX(CASE WHEN l.statusId = 3 THEN l.case_id END) AS rfi_case_id, ");
        builder.append("  MAX(CASE WHEN l.statusId = 3 THEN l.hitdata_id END) AS rfi_hitdata_id, ");
        // Average processing time
        builder.append(" AVG(CASE WHEN l.statusId IN (1, 2, 3) THEN TIMESTAMPDIFF(SECOND, s.created_at, l.created_at) END) AS average_processing_time_seconds ");
        builder.append(this.schema);


        return builder.toString();
    }

    @Override
    public TimeReportData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String searchedName = rs.getString("searched_name");
        final String searchInsertedTime = rs.getString("search_inserted_time");
        final int searchInsertedSeconds = rs.getInt("search_inserted_seconds");
        final int totalRelatedRecords = rs.getInt("total_related_records");
        final String allHitRecordInsertedTimes = rs.getString("all_hitrecord_inserted_times");

        final String closedCaseName = rs.getString("closed_case_name");
        final String caseClosedTime = rs.getString("case_closed_time");
        final int caseClosedSeconds = rs.getInt("case_closed_seconds");
        final int timeToCloseSeconds = rs.getInt("time_to_close_seconds");
        final int closedLevelId = rs.getInt("closed_level_id");
        final int closedCaseId = rs.getInt("closed_case_id");
        final int closedHitdataId = rs.getInt("closed_hitdata_id");

        final String escalatedCaseName = rs.getString("escalated_case_name");
        final String escalationTime = rs.getString("escalation_time");
        final int escalationSeconds = rs.getInt("escalation_seconds");
        final int timeToEscalationSeconds = rs.getInt("time_to_escalation_seconds");
        final int escalatedLevelId = rs.getInt("escalated_level_id");
        final int escalatedCaseId = rs.getInt("escalated_case_id");
        final int escalatedHitdataId = rs.getInt("escalated_hitdata_id");

        final String rfiCaseName = rs.getString("rfi_case_name");
        final String rfiTime = rs.getString("rfi_time");
        final int timeToRfiSeconds = rs.getInt("time_to_rfi_seconds");
        final int rfiLevelId = rs.getInt("rfi_level_id");
        final int rfiCaseId = rs.getInt("rfi_case_id");
        final int rfiHitdataId = rs.getInt("rfi_hitdata_id");

        final double averageProcessingTimeSeconds = rs.getDouble("average_processing_time_seconds");

        return TimeReportData.newInstance(
                searchedName, searchInsertedTime, searchInsertedSeconds, totalRelatedRecords, allHitRecordInsertedTimes,
                closedCaseName, caseClosedTime, caseClosedSeconds, timeToCloseSeconds, closedLevelId, closedCaseId, closedHitdataId,
                escalatedCaseName, escalationTime, escalationSeconds, timeToEscalationSeconds, escalatedLevelId, escalatedCaseId, escalatedHitdataId,
                rfiCaseName, rfiTime, timeToRfiSeconds, rfiLevelId, rfiCaseId, rfiHitdataId,
                averageProcessingTimeSeconds
        );
    }

}
