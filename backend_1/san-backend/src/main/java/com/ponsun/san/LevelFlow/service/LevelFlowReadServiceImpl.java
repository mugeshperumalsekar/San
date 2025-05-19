package com.ponsun.san.LevelFlow.service;

import com.ponsun.san.LevelFlow.data.LevelFlowData;
import com.ponsun.san.LevelFlow.rowmapper.LevelFlowRowMapper;
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
public class LevelFlowReadServiceImpl implements LevelFlowReadService{
    private final JdbcTemplate jdbcTemplate;
    private final LevelFlowRowMapper levelFlowRowMapper;

    @Override
    public List<LevelFlowData> fetchAllLevelFlow(Integer levelId,Integer statusId){
        Map<String,Object> parameters = new HashMap<>();
        final LevelFlowRowMapper rowmapper = new LevelFlowRowMapper();
        String Qry = "SELECT "+ rowmapper.tableSchema();
        String whereClause=" ";

        if(statusId==3)
            whereClause=" WHERE a.id=b.searchid AND a.id=c.search_id AND b.id=c.hitdata_id AND e.id=d.statusId AND c.level_id=d.levelId AND c.statusId= d.statusId AND d.passinglevelId=? AND c.valid=1 AND d.isAlive=1 AND d.statusId =3 ";
        else
            whereClause=" WHERE a.id=b.searchid AND a.id=c.search_id AND b.id=c.hitdata_id AND e.id=d.statusId AND c.level_id=d.levelId AND c.statusId= d.statusId AND d.passinglevelId=? AND c.valid=1 AND d.isAlive=1 AND d.statusId !=3 ";

        Qry = Qry + whereClause;

        final List<LevelFlowData> levelFlowData = jdbcTemplate.query(Qry,new Object[]{levelId},levelFlowRowMapper);
        return levelFlowData;
    }

}
