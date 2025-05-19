package com.ponsun.san.nameSearch.services;

import com.ponsun.san.nameSearch.data.NameSearchData;
import com.ponsun.san.nameSearch.rowmapper.NameSearchRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NameSearchReadServiceImpl implements NameSearchReadService {

    private final JdbcTemplate jdbcTemplate;
    private final NameSearchRowMapper nameSearchRowMapper;
    @Override
    public List<NameSearchData> fetchAllNameSearch(Integer kycId) {
        final NameSearchRowMapper rowMapper = new NameSearchRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE kycId = ? AND isScreening = 1 ";
        Qry = Qry + whereClause;
        final List<NameSearchData> nameSearchData = jdbcTemplate.query(Qry, new Object[]{kycId}, nameSearchRowMapper);
        return nameSearchData;
    }
}
