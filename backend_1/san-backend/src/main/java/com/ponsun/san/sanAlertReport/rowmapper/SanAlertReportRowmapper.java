package com.ponsun.san.sanAlertReport.rowmapper;
import com.ponsun.san.sanAlertReport.data.SanAlertReportData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
@Data
@Service
@Slf4j
public class SanAlertReportRowmapper implements RowMapper<SanAlertReportData> {

    private final String schema;

    public SanAlertReportRowmapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM search a, hitrecord b, hitrecord_lifecycle c, workflow_mapping d, STATUS e, admin_users f");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" e.name, a.name AS searchName, a.id AS searchId, b.id AS hitId, b.criminalid AS criminalId, " +
                "b.name AS criminalName, b.matchingscore AS matchingScore, c.remark AS remark, c.case_id AS caseId, b.fileType AS fileType, " +
                "a.created_at AS created_at, f.uid AS uid, " +
                "CASE WHEN d.statusId = 1 THEN 'Closed' " +
                "WHEN d.statusId IN (2, 3) THEN 'Open' " +
                "ELSE 'Unknown' END AS currentStatus ");  // Add the CASE statement for currentStatus
        builder.append(this.schema);
        return builder.toString();
    }


    @Override
    public SanAlertReportData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String name = rs.getString("name");
        final String searchName = rs.getString("searchName");
        final Integer searchId = rs.getInt("searchId");
        final Integer hitId = rs.getInt("hitId");
        final Integer criminalId = rs.getInt("criminalId");
        final String criminalName = rs.getString("criminalName");
        final Integer matchingScore=rs.getInt("matchingScore");
        final String remark = rs.getString("remark");
        final Integer caseId = rs.getInt("caseId");
        final Integer fileType= rs.getInt("fileType");
        final Integer uid= rs.getInt("uid");

        final String created_at = rs.getString("created_at");
        final String currentStatus = rs.getString("currentStatus");
        return new SanAlertReportData(name,searchName,searchId,hitId,criminalId,criminalName,matchingScore,remark,caseId,fileType,uid,created_at,currentStatus);

    }
}
