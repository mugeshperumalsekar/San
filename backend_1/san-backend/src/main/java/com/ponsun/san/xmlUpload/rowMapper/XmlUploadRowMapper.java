package com.ponsun.san.xmlUpload.rowMapper;

import com.ponsun.san.xmlUpload.data.XmlUploadData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class XmlUploadRowMapper implements RowMapper<XmlUploadData> {
    public final String schema;

    public XmlUploadRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM xml_uploads a, admin_users b,file_type c ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("a.id,a.file_name,a.file_size,a.record_count,a.processing_time,c.name AS FileType,a.created_at,b.userName AS userName,a.status ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public XmlUploadData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final String fileName = rs.getString("file_name");
        final Long fileSize = rs.getLong("file_size");
        final Integer recordCount = rs.getInt("record_count");
        final Double processingTime = rs.getDouble("processing_time");
        final String fileType = rs.getString("fileType");
        final String createdAt = rs.getString("created_at");
        final String userName = rs.getString("userName");
        final String status = rs.getString("status");
        return XmlUploadData.newInstance(id, fileName, fileSize, recordCount, processingTime, fileType, createdAt, userName, status);
    }
}
