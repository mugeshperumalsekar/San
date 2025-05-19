package com.ponsun.san.LevelFlow.rowmapper;

import com.ponsun.san.LevelFlow.data.LevelFlowData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
@Data
@Service
@Slf4j
public class LevelFlowRowMapper implements RowMapper<LevelFlowData> {
    private final String schema;

    public LevelFlowRowMapper(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM search a,hitrecord b, hitrecord_lifecycle c,workflow_mapping d,STATUS e");
        this.schema = builder.toString();
    }
    public String schema(){return this.schema;}

    public String tableSchema(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" e.name,a.name AS searchName , a.id AS searchId, b.id AS hitId, b.criminalid AS criminalId, b.name AS criminalName, b.matchingscore AS matchingScore,c.remark AS remark,c.case_id AS caseId,b.fileType AS fileType  ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public LevelFlowData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String name = rs.getString("name");
        final String searchName = rs.getString("searchName");
        final Integer searchId = rs.getInt("searchId");
        final Integer hitId = rs.getInt("hitId");
        final Integer criminalId = rs.getInt("criminalId");
        final String criminalName = rs.getString("criminalName");
        final Integer matchingScore=rs.getInt("matchingScore");
        final String remark = rs.getString("remark");
        final Integer caseId = rs.getInt("caseId");
        final Integer fileType= rs.getInt("fileType");
        return new LevelFlowData(name,searchName,searchId,hitId,criminalId,criminalName,matchingScore,remark,caseId,fileType);
    }
}
