package com.ponsun.san.sanReopen.service;

import com.ponsun.san.sanReopen.data.SanReopenData;
import com.ponsun.san.sanReopen.rowmapper.SanReopenRowmapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SanReopenDataWriteServiceImpl implements SanReopenDataWriteService {

    private final JdbcTemplate jdbcTemplate;
    private final SanReopenRowmapper sanReopenRowmapper;


    @Override
    public List<SanReopenData> fetchAllLReopen(List<Integer> uid, String startDate, String endDate) {
        // Generate placeholders
        String uidPlaceholders = String.join(",", Collections.nCopies(uid.size(), "?"));

        // Base SQL
        String sql = """
                 SELECT a.name AS searchName,a.id AS searchId,b.id AS hitId,b.criminalid AS criminalId,b.name AS criminalName,
                    closed.id AS closed_ids,closed.case_id AS caseId,
                    closed.created_at AS closed_created_at,closed.updated_at AS closed_updated_at,
                    closed.uid AS closed_uid, closed.statusId AS closed_statusId,
                    closed.search_id AS closed_search_id,closed.criminal_id AS closed_criminal_id,
                    closed.remark AS closed_remark,closed.passingLevelId AS closed_passingLevelId,
                    MIN(reopened.created_at) AS reopened_created_at, 
                    MAX(reopened.updated_at) AS reopened_updated_at, 
                    COUNT(reopened.id) AS reopen_count, 
                    GROUP_CONCAT(reopened.id ORDER BY reopened.updated_at ASC) AS reopen_ids,
                    GROUP_CONCAT(reopened.statusId ORDER BY reopened.updated_at ASC) AS reopen_statusId,
                    GROUP_CONCAT(reopened.case_id ORDER BY reopened.updated_at ASC) AS reopen_caseId,
                    GROUP_CONCAT(reopened.passingLevelId ORDER BY reopened.updated_at ASC) AS reopen_passingLevelId,
                    GROUP_CONCAT(reopened.uid ORDER BY reopened.updated_at ASC) AS reopen_uids,
                    GROUP_CONCAT(reopened.remark ORDER BY reopened.updated_at ASC SEPARATOR ' | ') AS reopen_remarks,
                    SEC_TO_TIME(IFNULL(AVG(ABS(TIMESTAMPDIFF(SECOND, closed.updated_at, reopened.created_at))), 0)) AS avg_reprocessing_time_seconds 
                FROM search a
                JOIN hitrecord b ON a.id = b.searchid
                JOIN hitrecord_lifecycle closed ON a.id = closed.search_id AND b.id = closed.hitdata_id
                LEFT JOIN hitrecord_lifecycle reopened 
                    ON closed.criminal_id = reopened.criminal_id
                    AND closed.search_id = reopened.search_id
                    AND reopened.id <> closed.id
                    AND reopened.statusId <> 1
                WHERE closed.statusId = 1
                AND closed.passingLevelId IN (2, 3, 4, 5)
                AND closed.uid IN (""" + uidPlaceholders + ")";

        if (startDate != null && endDate != null) {
            sql += " AND DATE(closed.created_at) BETWEEN ? AND ?";
        }

        sql += " GROUP BY closed.id ORDER BY closed.updated_at DESC";

        List<Object> params = new ArrayList<>(uid);
        if (startDate != null && endDate != null) {
            params.add(startDate);
            params.add(endDate);
        }

        return jdbcTemplate.query(sql, params.toArray(), new SanReopenRowmapper());
    }

}