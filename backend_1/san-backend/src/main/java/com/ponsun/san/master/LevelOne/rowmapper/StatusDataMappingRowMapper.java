package com.ponsun.san.master.LevelOne.rowmapper;


import com.ponsun.san.master.LevelOne.data.StatusDataMapping;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class StatusDataMappingRowMapper implements RowMapper<StatusDataMapping> {

    private final String schema;
    public StatusDataMappingRowMapper(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM `level_status_mapping` a,`status` b ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }
    public String tableSchema(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" b.id,b.name ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public StatusDataMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final String name = rs.getString("name");
        return StatusDataMapping.newInstance(id,name);
    }
}
