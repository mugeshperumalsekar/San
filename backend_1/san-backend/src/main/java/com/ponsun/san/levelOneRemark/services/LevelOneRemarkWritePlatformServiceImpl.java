package com.ponsun.san.levelOneRemark.services;

import com.ponsun.san.levelOneRemark.data.LevelOneRemarkData;
import com.ponsun.san.levelOneRemark.rowmapper.LevelOneRemarkRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LevelOneRemarkWritePlatformServiceImpl implements LevelOneRemarkWritePlatformService{
    private final JdbcTemplate jdbcTemplate;
    private final LevelOneRemarkRowMapper levelOneRemarkRowMapper;

    @Override
    public List<LevelOneRemarkData> fetchAllLevelOneRemark(Integer criminalId,Integer hitdataId,Integer levelId,Integer statusId){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("criminal_id", criminalId);
        parameters.put("hitdata_id", hitdataId);
        parameters.put("level_id", levelId);
        parameters.put("statusId", statusId);
        final LevelOneRemarkRowMapper rowMapper = new LevelOneRemarkRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE criminal_id = ? AND hitdata_id = ? AND level_id= ? AND statusId = ? ";
        Qry = Qry + whereClause;

        final List<LevelOneRemarkData> levelOneRemarkData = jdbcTemplate.query(Qry,levelOneRemarkRowMapper,new Object[]{criminalId,hitdataId,levelId,statusId});
        return levelOneRemarkData;
    }

    @Override
    public List<LevelOneRemarkData> fetchAllRIF(Integer levelId,Integer statusId){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("level_id", levelId);
        parameters.put("statusId", statusId);
        final LevelOneRemarkRowMapper rowMapper = new LevelOneRemarkRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE level_id= ? AND statusId = ? AND valid = 1";
        Qry = Qry + whereClause;

        final List<LevelOneRemarkData> levelOneRemarkData = jdbcTemplate.query(Qry,levelOneRemarkRowMapper,new Object[]{levelId,statusId});
        return levelOneRemarkData;
    }
}
