package com.ponsun.san.xmlReadFile.euSan.personCountry.service;

import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.euSan.personCountry.rowmpper.PersonCountryRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonCountryReadServiceImpl implements PersonCountryReadService{

    private final JdbcTemplate jdbcTemplate;
    private final PersonCountryRowMapper rowMapper;

    @Override
    public List<ArabicSanData> getAllPersonDetails() {
            String sql = "SELECT " +
                    "e.Entity_LogicalId AS person_id, " +
                    "e.Identification_Number, " +
                    "e.NameAlias_WholeName AS name, " +
                    "DATE_FORMAT(STR_TO_DATE(CONCAT_WS('-', e.BirthDate_Year, LPAD(e.BirthDate_Month, 2, '0'), LPAD(e.BirthDate_Day, 2, '0')), '%Y-%m-%d'), '%Y-%m-%d') AS dob, " +
                    "TRIM(BOTH ', ' FROM CONCAT_WS(', ', " +
                    "CASE WHEN LOWER(e.Address_CountryDescription) = LOWER(cr1.country_name) AND LOWER(e.Address_CountryIso2Code) = LOWER(cr1.iso_code) THEN cr1.country_name END, " +
                    "CASE WHEN LOWER(e.BirthDate_CountryDescription) = LOWER(cr2.country_name) AND LOWER(e.BirthDate_CountryIso2Code) = LOWER(cr2.iso_code) THEN cr2.country_name END, " +
                    "CASE WHEN LOWER(e.Identification_CountryDescription) = LOWER(cr3.country_name) AND LOWER(e.Identification_CountryIso2Code) = LOWER(cr3.iso_code) THEN cr3.country_name END, " +
                    "CASE WHEN LOWER(e.Citizenship_CountryDescription) = LOWER(cr4.country_name) AND LOWER(e.Citizenship_CountryIso2Code) = LOWER(cr4.iso_code) THEN cr4.country_name END)) AS country " +
                    "FROM eu_csv_file e " +
                    "LEFT JOIN country_reference cr1 ON LOWER(e.Address_CountryDescription) = LOWER(cr1.country_name) AND LOWER(e.Address_CountryIso2Code) = LOWER(cr1.iso_code) " +
                    "LEFT JOIN country_reference cr2 ON LOWER(e.BirthDate_CountryDescription) = LOWER(cr2.country_name) AND LOWER(e.BirthDate_CountryIso2Code) = LOWER(cr2.iso_code) " +
                    "LEFT JOIN country_reference cr3 ON LOWER(e.Identification_CountryDescription) = LOWER(cr3.country_name) AND LOWER(e.Identification_CountryIso2Code) = LOWER(cr3.iso_code) " +
                    "LEFT JOIN country_reference cr4 ON LOWER(e.Citizenship_CountryDescription) = LOWER(cr4.country_name) AND LOWER(e.Citizenship_CountryIso2Code) = LOWER(cr4.iso_code)" +
                    "WHERE e.NameAlias_NameLanguage='EN'";

            return jdbcTemplate.query(sql, new PersonCountryRowMapper());
        }
}
