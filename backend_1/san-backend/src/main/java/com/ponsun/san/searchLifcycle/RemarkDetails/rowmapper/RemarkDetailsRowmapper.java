package com.ponsun.san.searchLifcycle.RemarkDetails.rowmapper;

import com.ponsun.san.searchLifcycle.RemarkDetails.data.RemarkDetailsData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class RemarkDetailsRowmapper implements RowMapper<RemarkDetailsData> {
    public final String schema;

    public RemarkDetailsRowmapper(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM `hitrecord_lifecycle` a,`level` b,`status` c ");
        this.schema = builder.toString();
    }
    public String schema(){return this.schema;}
    public String tableSchema(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append("DATE_FORMAT (a.created_at,'%d-%m-%Y %h:%i %p') AS created_At,b.name AS LEVEL,c.name AS STATUS,remark  ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public RemarkDetailsData mapRow(ResultSet rs,int rowNum) throws SQLException{
        final String remark = rs.getString("remark");
        final String createdAt= rs.getString("created_At");
        final String level = rs.getString("level");
        final String status = rs.getString("status");
        return RemarkDetailsData.newInstance(remark,createdAt,level,status);
    }
}
