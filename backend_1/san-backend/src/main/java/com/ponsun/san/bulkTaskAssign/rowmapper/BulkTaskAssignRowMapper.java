package com.ponsun.san.bulkTaskAssign.rowmapper;


import com.ponsun.san.bulkTaskAssign.data.BulkTaskAssignData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class BulkTaskAssignRowMapper implements RowMapper<BulkTaskAssignData> {
    private final String schema;

    public BulkTaskAssignRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM san_bulk_task_assign a");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("a.assignTo as assignTo,");
        builder.append("a.assignBy as assignBy,");
        builder.append("a.searchName as searchName,");
        builder.append("a.searchId as searchId,");
        builder.append("a.isTaskAssigned as isTaskAssigned,");
        builder.append("a.matchingScore as matchingScore,");
        builder.append("a.uid as uid,");
        builder.append("a.euid as euid");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public BulkTaskAssignData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer assignTo = rs.getInt("assignTo");
        final Integer assignBy = rs.getInt("assignBy");
        final String searchName = rs.getString("searchName");
        final Integer searchId = rs.getInt("searchId");
        final Integer isTaskAssigned = rs.getInt("isTaskAssigned");
        final Integer matchingScore = rs.getInt("matchingScore");
        final Integer uid = rs.getInt("uid");
        final Integer euid = rs.getInt("euid");
        return BulkTaskAssignData.newInstance(assignTo, assignBy, searchName,searchId, isTaskAssigned,matchingScore,uid, euid);
    }
}
