package com.ponsun.san.entityScreening.rowmapper;

import com.ponsun.san.entityScreening.data.EntityScreeningData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class EntityScreeningRowMapper implements RowMapper<EntityScreeningData> {
    private final String schema;

    public EntityScreeningRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM hitrecord ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("id,criminalId,cycleId,display,matchingScore,name,searchId,statusNowId ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public EntityScreeningData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final Integer criminalId = rs.getInt("criminalId");
        final Integer cycleId = rs.getInt("cycleId");
        final String display = rs.getString("display");
        final Integer matchingScore = rs.getInt("matchingScore");
        final String name = rs.getString("name");
        final Integer searchId = rs.getInt("searchId");
        final Integer statusNowId = rs.getInt("statusNowId");
        return EntityScreeningData.newInstance(id, criminalId, cycleId, display, matchingScore, name, searchId, statusNowId, null);
    }
}