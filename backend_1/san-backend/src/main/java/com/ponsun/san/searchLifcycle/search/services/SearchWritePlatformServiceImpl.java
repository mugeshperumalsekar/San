package com.ponsun.san.searchLifcycle.search.services;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.search.data.SearchData;
import com.ponsun.san.searchLifcycle.search.data.SearchDataValidator;
import com.ponsun.san.searchLifcycle.search.domain.Search;
import com.ponsun.san.searchLifcycle.search.domain.SearchRepository;
import com.ponsun.san.searchLifcycle.search.domain.SearchRepositoryWrapper;
import com.ponsun.san.searchLifcycle.search.request.CreateSearchRequest;
import com.ponsun.san.searchLifcycle.search.request.UpdateSearchRequest;
import com.ponsun.san.searchLifcycle.search.rowmapper.SearchRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchWritePlatformServiceImpl implements SearchWritePlatformService {

    private final SearchRepository searchRepository;
    private final SearchRepositoryWrapper searchRepositoryWrapper;
    private final SearchDataValidator searchDataValidator;
    private final SearchRowMapper searchRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Response createSearch(CreateSearchRequest createSearchRequest) {
        try {
            this.searchDataValidator.validateSaveSearchData(createSearchRequest);
             Search search = Search.create(createSearchRequest);
            search=this.searchRepository.saveAndFlush(search);
            return Response.of(search.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }
    @Override
    @Transactional
    public Response updateSearch(Integer id, UpdateSearchRequest updateSearchRequest) {
        try {
            this.searchDataValidator.validateUpdateSearchData(updateSearchRequest);
            final Search search = this.searchRepositoryWrapper.findOneWithNotFoundDetection(id);
            search.update(updateSearchRequest);
            this.searchRepository.saveAndFlush(search);
            return Response.of(Long.valueOf(search.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public Response blockSearch(Integer id) {
        try {
            final Search search = this.searchRepositoryWrapper.findOneWithNotFoundDetection(id);
            search.setValid(false);
            search.setStatus(Status.DELETE);
            search.setUpdatedAt(LocalDateTime.now());
            this.searchRepository.saveAndFlush(search);
            return Response.of(Long.valueOf(id));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response unblockSearch(Integer id) {
        try {
            final Search search = this.searchRepositoryWrapper.findOneWithNotFoundDetection(id);
            search.setValid(true);
            search.setStatus(Status.DELETE);
            search.setUpdatedAt(LocalDateTime.now());
            this.searchRepository.saveAndFlush(search);
            return Response.of(Long.valueOf(id));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<SearchData> fetchByIsBulkSearch(Integer isBulkSearch) {
        final SearchRowMapper rowMapper = new SearchRowMapper();
        String Qry = "SELECT "  + rowMapper.tableSchema();
        String whereClause = " WHERE cs.isBulkSearch = ?  AND cs.STATUS = 'A'";
        Qry = Qry + whereClause;
        final List<SearchData> searchDataList = jdbcTemplate.query(Qry, searchRowMapper,
                new Object[] {isBulkSearch}
        );
        return searchDataList;
    }

    @Override
    @Transactional
    public List<SearchData> fetchIsBulkSearch() {
        final SearchRowMapper rowMapper = new SearchRowMapper();
        String Qry = "SELECT "  + rowMapper.tableSchema();
        String whereClause = " WHERE cs.isBulkSearch = 1  AND cs.STATUS = 'A' ";
        Qry = Qry + whereClause;
        final List<SearchData> searchDataList = jdbcTemplate.query(Qry, searchRowMapper,
                new Object[] {}
        );
        return searchDataList;
    }


    @Override
    @Transactional
    public List<SearchData> fetchAllSearch() {
        final SearchRowMapper rowMapper = new SearchRowMapper();
        String Qry = "SELECT "  + rowMapper.tableSchema();
        String whereClause = " WHERE cs.uid = b.id AND cs.isBulkSearch = 0";
        Qry = Qry + whereClause;
        final List<SearchData> searchDataList = jdbcTemplate.query(Qry, searchRowMapper,
                new Object[] {}
        );
        return searchDataList;
    }

}
