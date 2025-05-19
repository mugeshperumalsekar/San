package com.ponsun.san.ofac.Count.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.ponsun.san.ofac.Count.data.SanctionDetailData;
import com.ponsun.san.ofac.Count.rowmapper.EuSanctionRowMapper;
import com.ponsun.san.ofac.Count.rowmapper.UkSanctionRowMapper;
import com.ponsun.san.ofac.Count.rowmapper.UnSanctionRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SanctionDetailReadServiceImpl implements SanctionDetailReadService {
    private final JdbcTemplate jdbcTemplate;
    private final EuSanctionRowMapper euSanctionRowMapper;
    private final UkSanctionRowMapper ukSanctionRowMapper;
    private final UnSanctionRowMapper unSanctionRowMapper;
    private final HazelcastInstance hazelcastInstance;

    private IMap<String, SanctionDetailData> getEuCacheMap() {
        return hazelcastInstance.getMap("EuOfacDataDetail");
    }
    private IMap<String, SanctionDetailData> getUkCacheMap() {
        return hazelcastInstance.getMap("UkOfacDataDetail");
    }
    private IMap<String, SanctionDetailData> getUnCacheMap() {
        return hazelcastInstance.getMap("UNOfacDataDetail");
    }


    @Override
    public SanctionDetailData fetchSanctionData(Integer ids, Integer SanType) {
        SanctionDetailData sanData  = new SanctionDetailData();

        if(SanType==2)
            sanData = getEuCacheMap().get("EU"+ids);
        if(SanType==3)
            sanData = getUkCacheMap().get("UK"+ids);
        if(SanType==4)
            sanData = getUnCacheMap().get("UN"+ids);

        return sanData;

    }

    @Override
    public SanctionDetailData fetchEuSanctionData(Integer entityLogicalId) {
        String Qry = "SELECT " + euSanctionRowMapper.tableSchema();
        String whereClause = " WHERE Entity_logical_id = ? GROUP BY Entity_logical_id";
        Qry = Qry + whereClause;

        return jdbcTemplate.queryForObject(Qry, new Object[]{entityLogicalId}, euSanctionRowMapper);
    }

    @Override
    public SanctionDetailData fetchUkSanctionData(Integer groupId) {
        String Qry = "SELECT " + ukSanctionRowMapper.tableSchema();
        String whereClause = " WHERE Group_id = ? GROUP BY Group_id";
        Qry = Qry + whereClause;

        return jdbcTemplate.queryForObject(Qry, new Object[]{groupId}, ukSanctionRowMapper);
    }

    @Override
    public SanctionDetailData fetchUnSanctionData(Integer dataid) {
        String Qry = "SELECT " + unSanctionRowMapper.tableSchema();
        String whereClause = " WHERE dataid = ?";
        Qry = Qry + whereClause;

        return jdbcTemplate.queryForObject(Qry, new Object[]{dataid}, unSanctionRowMapper);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<SanctionDetailData> fetchAllEUSanctionData() {

        String Qry = "SELECT " + euSanctionRowMapper.tableSchema();
        String whereClause = " GROUP BY Entity_logical_id";
        Qry = Qry + whereClause;
        List<SanctionDetailData> sanctionDetailDataList= jdbcTemplate.query(Qry,euSanctionRowMapper);

        for (SanctionDetailData sanctionDetailData : sanctionDetailDataList)
        {
            IMap<String, SanctionDetailData> cacheMap = getEuCacheMap();
            Integer ids = sanctionDetailData.getIds();
            cacheMap.putIfAbsent("EU"+ids,sanctionDetailData);
        }
        return sanctionDetailDataList;
    }

    public List<SanctionDetailData> fetchAllUKSanctionData() {
        String Qry = "SELECT " + ukSanctionRowMapper.tableSchema();
        String whereClause = "  WHERE Group_id !=' ' GROUP BY Group_id, Group_Type " +
                "HAVING " +
                "    Nationality_country != '' OR " +
                "    Citi_country != '' OR " +
                "    Birt_country != '' OR " +
                "    Iden_country != '' ";
        Qry = Qry + whereClause;
        List<SanctionDetailData> sanctionDetailDataList= jdbcTemplate.query(Qry,ukSanctionRowMapper);

        for (SanctionDetailData sanctionDetailData : sanctionDetailDataList)
        {
            IMap<String, SanctionDetailData> cacheMap = getUkCacheMap();
            Integer ids = sanctionDetailData.getIds();
            cacheMap.putIfAbsent("UK"+ids,sanctionDetailData);
        }
        return sanctionDetailDataList;
    }

    ////////////////////
    public List<SanctionDetailData> fetchAllUnSanctionData() {
        String Qry = "SELECT " + unSanctionRowMapper.tableSchema();
        String whereClause = " group by dataid";
        Qry = Qry + whereClause;
        List<SanctionDetailData> sanctionDetailDataList= jdbcTemplate.query(Qry,unSanctionRowMapper);

        for (SanctionDetailData sanctionDetailData : sanctionDetailDataList)
        {
            IMap<String, SanctionDetailData> cacheMap = getUnCacheMap();
            Integer ids = sanctionDetailData.getIds();
            cacheMap.putIfAbsent("UN"+ids,sanctionDetailData);
        }
        return sanctionDetailDataList;
    }

    public void evictAllEuSanctionData() {
        log.info("Evicting all EU Sanction Data from cache");
        getEuCacheMap().clear(); // Clears the entire cache
    }

    public void evictAllUkSanctionData() {
        log.info("Evicting all UK Sanction Data from cache");
        getUkCacheMap().clear(); // Clears the entire cache
    }

    public void evictAllUnSanctionData() {
        log.info("Evicting all UN Sanction Data from cache");
        getUnCacheMap().clear(); // Clears the entire cache
    }

    public void evictAllCaches() {
        evictAllEuSanctionData();
        evictAllUkSanctionData();
        evictAllUnSanctionData();

    }
}
