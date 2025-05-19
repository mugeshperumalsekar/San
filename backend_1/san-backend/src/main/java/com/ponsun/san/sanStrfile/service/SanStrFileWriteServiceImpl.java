package com.ponsun.san.sanStrfile.service;

import com.ponsun.san.sanStrfile.data.SanStrFileData;
import com.ponsun.san.sanStrfile.rowmapper.SanStrFileRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanStrFileWriteServiceImpl implements SanStrFileWriteService {
    private final JdbcTemplate jdbcTemplate;
    private final SanStrFileRowMapper sanStrFileRowMapper;

    public SanStrFileWriteServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sanStrFileRowMapper = new SanStrFileRowMapper();
    }

    @Override
    public List<SanStrFileData> fetchAllLStr(String startDate, String endDate) {
        String sql = """
                SELECT
                                    COUNT(*) AS str_file_count,
                                    a.name AS searchName,
                                    a.id AS searchId,
                                    b.id AS hitId,
                                    c.case_id AS caseId,
                                    b.criminalid AS criminalId,
                                    b.name AS criminalName,
                                    d.statusId,
                                    d.passinglevelId
                                FROM search a
                                JOIN hitrecord b ON a.id = b.searchid
                                JOIN hitrecord_lifecycle c ON a.id = c.search_id AND b.id = c.hitdata_id
                                JOIN workflow_mapping d ON c.level_id = d.levelId AND c.statusId = d.statusId
                                JOIN STATUS e ON e.id = d.statusId
                                WHERE e.id = 2
                                  AND d.passinglevelId = 5
                                  AND DATE(c.created_at) BETWEEN ? AND ?
                                GROUP BY
                                    a.name, a.id,
                                    b.id, b.criminalid, b.name,
                                    c.case_id,
                                    d.statusId, d.passinglevelId;
                """;

        return jdbcTemplate.query(sql, sanStrFileRowMapper, startDate, endDate);
    }
}
