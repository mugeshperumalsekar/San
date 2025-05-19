package com.ponsun.san.fileType.rowmapper;

import com.ponsun.san.fileType.data.FileTypeData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
@Slf4j
public class fileTypeRowMapper implements RowMapper<FileTypeData> {
    private final String schema;
    public fileTypeRowMapper(){
        final StringBuilder builder = new StringBuilder(200);
       builder.append("FROM file_type ft ");
       this.schema = builder.toString();
    }

    public String schema(){return this.schema;}

    public String tableSchema(){
        final StringBuilder builder = new StringBuilder(200);
        builder.append("ft.id as id");
        builder.append("ft.name as name");
        builder.append("ft.valid as valid");
        builder.append("ft.uid as uid");
        builder.append("ft.euid as euid");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public FileTypeData mapRow(ResultSet rs , int rowNum) throws SQLException {
        final Integer id = rs.getInt("id");
        final String name = rs.getString("name");
        final Boolean valid = rs.getBoolean("valid");
        final Integer uid = rs.getInt("uid");
        final Integer euid = rs.getInt("euid");
        return FileTypeData.newInstance(id,name,valid,uid,euid);
    }

}
