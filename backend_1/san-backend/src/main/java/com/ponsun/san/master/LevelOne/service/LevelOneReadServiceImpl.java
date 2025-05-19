package com.ponsun.san.master.LevelOne.service;
import com.ponsun.san.master.LevelOne.data.LevelOneData;
import com.ponsun.san.master.LevelOne.data.StatusDataMapping;
import com.ponsun.san.master.LevelOne.rowmapper.LevelOneRowMapper;
import com.ponsun.san.master.LevelOne.rowmapper.StatusDataMappingRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LevelOneReadServiceImpl implements LevelOneReadService {
    private final LevelOneRowMapper levelOneRowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final StatusDataMappingRowMapper statusDataMappingRowMapper;

    @Override
    public List<LevelOneData> fetchLevelOneData(Integer levelId) {
        final LevelOneRowMapper rowMapper = new LevelOneRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE a.statusId=b.id AND levelId=? ";
        Qry = Qry + whereClause;
        final List<LevelOneData> LevelOneData = jdbcTemplate.query(Qry, levelOneRowMapper,
                new Object[]{levelId}
        );
        return LevelOneData;
    }

    @Override
    public List<StatusDataMapping> fetchStatusMappingData(Integer levelId) {
        final StatusDataMappingRowMapper rowMapper = new StatusDataMappingRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE a.statusId=b.id AND a.levelId=? AND a.status='A' ";
        Qry = Qry + whereClause;
        final List<StatusDataMapping> LevelOneData = jdbcTemplate.query(Qry, statusDataMappingRowMapper,
                new Object[]{levelId}
        );
        return LevelOneData;
    }
}
