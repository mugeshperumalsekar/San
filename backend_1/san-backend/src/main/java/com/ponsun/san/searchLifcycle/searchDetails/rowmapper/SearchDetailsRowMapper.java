package com.ponsun.san.searchLifcycle.searchDetails.rowmapper;

import com.ponsun.san.searchLifcycle.searchDetails.data.SearchDetailsData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class SearchDetailsRowMapper implements RowMapper<SearchDetailsData> {
    private final String schema;
    public SearchDetailsRowMapper(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM search_details sd");
        this.schema = builder.toString();
    }
    public String schema(){return this.schema;}

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("sd.id as id, ");
        builder.append("sd.name as name, ");
        builder.append("sd.matching_score as matching_score, ");
        builder.append("sd.list_id as listId, ");
        builder.append("sd.type_id as typeId, ");
        builder.append("sd.state_id as stateId, ");
        builder.append("sd.country_id as countryId, ");
        builder.append("sd.identity as identity, ");
        builder.append("sd.level_id as levelId, ");
        builder.append("sd.uid as uid, ");
        builder.append("sd.valid as valid, ");
        builder.append("sd.kycId as kycId ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public SearchDetailsData mapRow(ResultSet cs,int rowNum) throws SQLException{
        final Integer id = cs.getInt("id");
        final String name = cs.getString("name");
        final Double matching_score = cs.getDouble("matching_score");
        final Integer listId = cs.getInt("listId");
        final Integer typeId = cs.getInt("typeId");
        final Integer stateId = cs.getInt("stateId");
        final Integer countryId = cs.getInt("countryId");
        final String identity = cs.getString("identity");
        final Long levelId = cs.getLong("levelId");
        final Integer uid = cs.getInt("uid");
        final Boolean valid = cs.getBoolean("valid");
        final Integer kycId = cs.getInt("kycId");
        return SearchDetailsData.newInstance(id,name,matching_score,listId,typeId,stateId,countryId,identity,levelId,uid,valid,kycId);
    }
}
