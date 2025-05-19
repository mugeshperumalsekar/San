package com.ponsun.san.master.levelMapping.rowmapper;

import com.ponsun.san.master.levelMapping.data.LevelMappingData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class LevelMappingRowMapper implements RowMapper<LevelMappingData> {

    private final String schema;

    public LevelMappingRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM workflow_mapping a ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("a.id as id ,");
        builder.append("a.levelId as levelId ,");
        builder.append("a.statusId as statusId ,");
        builder.append("a.passingLevelId as passingLevelId ,");
        builder.append("a.isAlive as isAlive ,");
        builder.append("a.uid as uid ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override

    public LevelMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final Integer levelId = rs.getInt("levelId");
        final Integer statusId = rs.getInt("statusId");
        final Integer passingLevelId = rs.getInt("passingLevelId");
        final Integer isAlive = rs.getInt("isAlive");
        final Integer uid = rs.getInt("uid");
        return LevelMappingData.newInstance(id, levelId, statusId, passingLevelId,isAlive,uid);
    }
}