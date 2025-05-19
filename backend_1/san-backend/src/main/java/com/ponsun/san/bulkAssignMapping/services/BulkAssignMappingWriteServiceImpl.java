package com.ponsun.san.bulkAssignMapping.services;


import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingValidator;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMapping;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMappingRepository;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMappingWrapper;
import com.ponsun.san.bulkAssignMapping.request.CreateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.request.UpdateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.rowmapper.BulkAssignMappingRowMapper;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.rowmapper.HitRecordRowMapper;
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
public class BulkAssignMappingWriteServiceImpl implements BulkAssignMappingWriteService {
    private final BulkAssignMappingRepository bulkAssignMappingRepository;
    private final BulkAssignMappingWrapper bulkAssignMappingWrapper;
    private final BulkAssignMappingValidator bulkAssignMappingValidator;
    private final BulkAssignMappingRowMapper bulkAssignMappingRowMapper;
    private final HitRecordRowMapper hitRecordRowMapper;
    private final JdbcTemplate jdbcTemplate;

@Override
    @Transactional
    public Response createBulkAssignMapping(CreateBulkAssignMappingRequest createBulkAssignMappingRequest) {
        try {
            this.bulkAssignMappingValidator.validateSaveBulkAssignMapping(createBulkAssignMappingRequest);
            final BulkAssignMapping bulkAssignMapping = BulkAssignMapping.create(createBulkAssignMappingRequest);
            this.bulkAssignMappingRepository.saveAndFlush(bulkAssignMapping);
            return Response.of(Long.valueOf(bulkAssignMapping.getId()));
        } catch (DataIntegrityViolationException e) {
            log.error("Error creating BulkAssignMapping: {}", e.getMessage(), e);
            throw new EQAS_SAN_ApplicationException("Error creating BulkAssignMapping:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateBulkAssignMapping(Integer id, UpdateBulkAssignMappingRequest updateBulkAssignMappingRequest) {
        try {
            this.bulkAssignMappingValidator.validateUpdateBulkAssignMapping(updateBulkAssignMappingRequest);
            final BulkAssignMapping bulkAssignMapping = this.bulkAssignMappingWrapper.findOneWithNotFoundDetection(id);
            bulkAssignMapping.update(updateBulkAssignMappingRequest);
            this.bulkAssignMappingRepository.saveAndFlush(bulkAssignMapping);
            return Response.of(Long.valueOf(bulkAssignMapping.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response deactive(Integer id, Integer euid) {
        try {
            BulkAssignMapping BulkAssignMapping = this.bulkAssignMappingWrapper.findOneWithNotFoundDetection(id);
            BulkAssignMapping.setEuid(euid);
            BulkAssignMapping.setStatus(Status.DELETE);
            BulkAssignMapping.setUpdatedAt(LocalDateTime.now());
            return Response.of(Long.valueOf(BulkAssignMapping.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<BulkAssignMappingData> fetchAllRecordData(Integer searchId) {
        final BulkAssignMappingRowMapper rowMapper = new BulkAssignMappingRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE a.searchId = ? ";
        Qry = Qry + whereClause;
        final List<BulkAssignMappingData> bulkAssignMappingData = jdbcTemplate.query(Qry, bulkAssignMappingRowMapper,
                new Object[]{searchId}
        );
        return bulkAssignMappingData;
    }

@Override
@Transactional
    public List<BulkAssignMappingData> getHitRecordsBySearchId(Integer searchId) {
    String sql = "SELECT a.id AS id, a.bulkAssignId AS bulkAssignId, a.searchId AS searchId, " +
            "a.hitId AS hitId, b.name AS name, b.id AS hit, a.uid AS uid, a.euid AS euid " +
            "FROM san_bulk_assign_mapping a, hitrecord b " +
            "WHERE a.searchId = ? AND b.id = a.hitId";
    return jdbcTemplate.query(sql, new Object[]{searchId}, bulkAssignMappingRowMapper);
    }

}
