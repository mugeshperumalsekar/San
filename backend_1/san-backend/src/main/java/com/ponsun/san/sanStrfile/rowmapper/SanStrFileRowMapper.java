package com.ponsun.san.sanStrfile.rowmapper;

import com.ponsun.san.sanStrfile.data.SanStrFileData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class SanStrFileRowMapper implements RowMapper<SanStrFileData> {
    @Override
    public SanStrFileData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SanStrFileData(
                rs.getInt("str_file_count"),
                rs.getString("searchName"),
                rs.getInt("searchId"),
                rs.getInt("hitId"),
                rs.getInt("caseId"),
                rs.getInt("criminalId"),
                rs.getString("criminalName"),
                rs.getInt("statusId"),       // Map statusId
                rs.getInt("passinglevelId")  // Map passinglevelId
        );
    }
}
