package com.ponsun.san.xmlReadFile.euSan.personCountry.rowmpper;

import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Data
@Slf4j
public class PersonCountryRowMapper implements RowMapper<ArabicSanData> {

    @Override
    public ArabicSanData mapRow(ResultSet rs, int rowNum) throws SQLException {

        ArabicSanData dto = new ArabicSanData();
        dto.setPersonId(rs.getInt("person_id"));
        dto.setIdValue(rs.getString("Identification_Number"));
        dto.setName(rs.getString("name"));
        dto.setFullDate(rs.getString("dob"));
        dto.setCountryName(rs.getString("country"));
        return dto;
    }
}
