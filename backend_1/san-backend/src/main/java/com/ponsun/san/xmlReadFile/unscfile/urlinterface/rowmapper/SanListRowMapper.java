package com.ponsun.san.xmlReadFile.unscfile.urlinterface.rowmapper;


import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class SanListRowMapper implements RowMapper<ArabicSanData> {

    private final String schema;

    public SanListRowMapper() {
        final StringBuilder builder = new StringBuilder(300);
        builder.append("FROM individual_1 1 ")
                .append("LEFT JOIN individual_2 2 ON 1.entity_class_2_id = 2.id ")
                .append("LEFT JOIN individual_3 3 ON 1.entity_class_3_id = 3.id ")
                .append("LEFT JOIN individual_4 4 ON 1.entity_class_4_id = 4.id ");
        this.schema = builder.toString();
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(300);
        builder.append("p.id AS person_id, ")
                .append("p.name AS name, ")
                .append("s.full_date AS full_date, ")
                .append("i.id_value AS id_value, ")
                .append("c.country_name AS country_name ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public ArabicSanData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final int personId = rs.getInt("person_id");
        final String name = rs.getString("name");
        final String fullDate = rs.getString("full_date");
        final String idValue = rs.getString("id_value");
        final String countryName = rs.getString("country_name");

        return new ArabicSanData(personId, name, fullDate, idValue, countryName);
    }
}
