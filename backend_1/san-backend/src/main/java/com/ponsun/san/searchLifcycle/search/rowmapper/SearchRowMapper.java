package com.ponsun.san.searchLifcycle.search.rowmapper;


import com.ponsun.san.searchLifcycle.search.data.SearchData;
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
public class SearchRowMapper implements RowMapper<SearchData> {
    private final String schema;

    public SearchRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM search cs , admin_users b  ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("cs.id as id, ");
        builder.append("cs.name as name, ");
        builder.append("cs.matching_score as matching_score, ");
        builder.append("cs.list_id as listId, ");
        builder.append("cs.type_id as typeId, ");
        builder.append("cs.state_id as stateId, ");
        builder.append("cs.country_id as countryId, ");
        builder.append("cs.identity as identity, ");
        builder.append("cs.level_id as levelId, ");
        builder.append("cs.uid as uid, ");
        builder.append("cs.valid as valid, ");
        builder.append("cs.kycId as kycId, ");
        builder.append("cs.isBulkSearch as isBulkSearch, ");
        builder.append("cs.created_at as created_at , ");
        builder.append("cs.isTaskAssigned as isTaskAssigned,  ");
        builder.append("cs.entryType as entryType,  ");
        builder.append("cs.accountNumber as accountNumber,  ");
        builder.append("b.userName as createdBy  ");

        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public SearchData mapRow(ResultSet cs, int rowNum) throws SQLException {
        final Integer id = cs.getInt("id");
        final String name = cs.getString("name");
        final Double matching_score = cs.getDouble("matching_score");
        final Integer listId = cs.getInt("listId");
        final Integer typeId = cs.getInt("typeId");
        final Integer stateId = cs.getInt("stateId");
        final Integer countryId = cs.getInt("countryId");
        final String identity = cs.getString("identity");
        final Long levelId = cs.getLong("levelId");
        final Long uid = cs.getLong("uid");
        final Boolean valid = cs.getBoolean("valid");
        final Integer kycId = cs.getInt("kycId");
        final Integer isBulkSearch = cs.getInt("isBulkSearch");
        final LocalDateTime created_at = cs.getTimestamp("created_at").toLocalDateTime();
        final Integer isTaskAssigned = cs.getInt("isTaskAssigned");
        final Integer entryType = cs.getInt("entryType");
        final String createdBy = cs.getString("createdBy");
        final String accountNumber = cs.getString("accountNumber");

        return SearchData.newInstance(id, name, matching_score, listId, typeId, stateId, countryId, identity, levelId, uid, valid, kycId, isBulkSearch,created_at,isTaskAssigned,entryType,createdBy , accountNumber);
    }
}
