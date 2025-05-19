package com.ponsun.san.master.LevelOne.rowmapper;


import com.ponsun.san.master.LevelOne.data.LevelOneData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class LevelOneRowMapper implements RowMapper<LevelOneData> {

    private final String schema;
    public LevelOneRowMapper(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM workflow_mapping a, STATUS b ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }
    public String tableSchema(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append("  b.id as statusId , a.id , a.levelId,b.name AS STATUS,a.passingLevelId,a.isAlive,b.name AS STATUS,a.passingLevelId,a.isAlive ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public LevelOneData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final Integer levelId = rs.getInt("levelId");
        final String STATUS = rs.getString("STATUS");
        final Integer passingLevelId = rs.getInt("passingLevelId");
        final Integer isAlive = rs.getInt("isAlive");
        final Integer statusId = rs.getInt("statusId");
        return LevelOneData.newInstance(id,levelId,STATUS,passingLevelId,isAlive,statusId);
    }
}
