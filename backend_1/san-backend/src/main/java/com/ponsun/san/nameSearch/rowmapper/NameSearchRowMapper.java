package com.ponsun.san.nameSearch.rowmapper;


import com.ponsun.san.nameSearch.data.NameSearchData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class NameSearchRowMapper implements RowMapper<NameSearchData> {

    private final String schema;

    public NameSearchRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM search a ");
        this.schema = builder.toString();
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" screeningType AS screeningType ,applicantFormId,kycId, NAME as answer  , isScreening ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public NameSearchData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer screeningType = rs.getInt("screeningType");
        final Integer kycId  = rs.getInt("kycId");
        final String question = "";
        final String answer = rs.getString("answer");
        final Integer applicantFormId = rs.getInt("applicantFormId");
        final Integer isScreening = rs.getInt("isScreening");
        return new NameSearchData(screeningType,kycId,question,answer,applicantFormId,isScreening);
    }
}
