package com.ponsun.san.bulkTaskAssignView.rowmapper;



import com.ponsun.san.bulkTaskAssignView.data.BulkTaskAssignViewData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Data
@Service
@Slf4j
public class BulkTaskAssignViewRowMapper implements RowMapper<BulkTaskAssignViewData> {

    private final String schema;

    public BulkTaskAssignViewRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM san_bulk_task_assign a,admin_users b ");
        this.schema = builder.toString();
    }
    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" b.id AS uid, a.searchName , b.userName AS userName ,a.searchId AS searchId,a.matchingScore AS matchingScore,a.created_at AS created_at  ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public BulkTaskAssignViewData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer uid = rs.getInt("uid");
        final String searchName = rs.getString("searchName");
        final String userName = rs.getString("userName");
        final Integer searchId = rs.getInt("searchId");
        final Integer matchingScore = rs.getInt("matchingScore");
        final LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
        return new BulkTaskAssignViewData(uid , searchName , userName,searchId,matchingScore,created_at);
    }
}
