package com.ponsun.san.master.statusMapping.rowmapper;

import com.ponsun.san.master.statusMapping.data.StatusMappingData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class StatusMappingRowMapper implements RowMapper<StatusMappingData> {

    private final String schema;

    public StatusMappingRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM level_status_mapping a ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" a.id as id ,");
        builder.append(" a.levelId as levelId ,");
        builder.append(" a.statusId as statusId ,");
        builder.append(" a.uid as uid ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public StatusMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final Integer levelId = rs.getInt("levelId");
        final Integer statusId = rs.getInt("statusId");
        final Integer uid = rs.getInt("uid");
        return StatusMappingData.newInstance(id, levelId, statusId,uid);
    }
}