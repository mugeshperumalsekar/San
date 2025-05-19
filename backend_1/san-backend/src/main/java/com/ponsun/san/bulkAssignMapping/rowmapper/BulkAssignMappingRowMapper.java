package com.ponsun.san.bulkAssignMapping.rowmapper;


import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class BulkAssignMappingRowMapper implements RowMapper<BulkAssignMappingData> {
    private final String schema;

    public BulkAssignMappingRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM san_bulk_assign_mapping a , hitreacord b");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("a.id as id,");
        builder.append("a.bulkAssignId as bulkAssignId,");
        builder.append("a.searchId as searchId,");
        builder.append("a.hitId as hitId,");
        builder.append("a.filetype as filetype,");
        builder.append("b.hitName as hitName,");
        builder.append("b.hit as hit,");
        builder.append("a.uid as uid,");
        builder.append("a.euid as euid");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public BulkAssignMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final Integer bulkAssignId = rs.getInt("bulkAssignId");
        final Integer searchId = rs.getInt("searchId");
        final Integer hitId = rs.getInt("hitId");
        final Integer filetype = rs.getInt("filetype");
        final String name = rs.getString("name");
        final Integer hit = rs.getInt("hit");
        final Integer uid = rs.getInt("uid");
        final Integer euid = rs.getInt("euid");
        return BulkAssignMappingData.newInstance(id,bulkAssignId, searchId, hitId,filetype,name , hit, uid, euid);
    }
}
