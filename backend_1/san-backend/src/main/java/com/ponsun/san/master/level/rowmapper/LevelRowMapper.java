package com.ponsun.san.master.level.rowmapper;


import com.ponsun.san.master.level.data.LevelData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class LevelRowMapper implements RowMapper<LevelData> {

    private final String schema;

    public LevelRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM level a ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("a.id as id,");
        builder.append("a.name as name,");
        builder.append("a.uid as uid,");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public LevelData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final String name = rs.getString("name");
        final Integer uid = rs.getInt("uid");
        return LevelData.newInstance(id, name, uid);
    }
}