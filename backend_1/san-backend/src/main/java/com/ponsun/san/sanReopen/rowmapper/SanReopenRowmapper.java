package com.ponsun.san.sanReopen.rowmapper;

import com.ponsun.san.sanReopen.data.SanReopenData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class SanReopenRowmapper implements RowMapper<SanReopenData> {
    @Override
    public SanReopenData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SanReopenData(
                rs.getInt("closed_case_id"),
                rs.getInt("caseId"),
                rs.getInt("hitId"),
                rs.getString("closed_created_at"),
                rs.getString("closed_updated_at"),
                rs.getString("closed_remark"),
                rs.getString("searchName"),
                rs.getString("criminalName"),
                rs.getInt("closed_uid"),
                rs.getInt("closed_statusId"),
                rs.getInt("closed_search_id"),
                rs.getInt("closed_criminal_id"),
                rs.getInt("closed_passingLevelId"),
                rs.getString("reopened_created_at"),
                rs.getString("reopened_updated_at"),
                rs.getInt("reopen_count"),
                rs.getString("reopen_ids"),
                rs.getString("reopen_statusId"),

                rs.getString("reopen_passingLevelId"),
                rs.getString("reopen_uids"), // ✅ FIX: Now mapping `reopen_uids`
                rs.getString("reopen_remarks"),
                rs.getString("avg_reprocessing_time_seconds") // ✅ FIXED: Now mapping seconds instead of TIME
        );
    }
}



