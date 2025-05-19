package com.ponsun.san.bulkTaskAssign.services;


import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingValidator;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMapping;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMappingRepository;
import com.ponsun.san.bulkAssignMapping.request.CreateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.rowmapper.BulkAssignMappingRowMapper;
import com.ponsun.san.bulkAssignMapping.services.BulkAssignMappingWriteService;
import com.ponsun.san.bulkTaskAssign.data.BulkTaskAssignData;
import com.ponsun.san.bulkTaskAssign.data.BulkTaskAssignValidator;
import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssign;
import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssignRepository;
import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssignWrapper;
import com.ponsun.san.bulkTaskAssign.request.CreateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.request.UpdateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.rowmapper.BulkTaskAssignRowMapper;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.dto.RecordDTO;
import com.ponsun.san.excelimport.service.ExcelReadService;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.data.HitRecordData;
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
public class BulkTaskAssignWriteServiceImpl implements BulkTaskAssignWriteService {
    private final BulkTaskAssignRepository bulkTaskAssignRepository;
    private final BulkTaskAssignWrapper bulkTaskAssignWrapper;
    private final BulkTaskAssignValidator bulkTaskAssignValidator;
    private final HitRecordRowMapper hitRecordRowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final BulkAssignMappingWriteService bulkAssignMappingWriteService;
    private final ExcelReadService excelReadService;
    private final BulkAssignMappingValidator bulkAssignMappingValidator;
    private final BulkAssignMappingRepository bulkAssignMappingRepository;
    private final BulkTaskAssignRowMapper bulkTaskAssignRowMapper;
    private final BulkAssignMappingRowMapper bulkAssignMappingRowMapper;


    @Override
    @Transactional
    public Response createBulkTaskAssign(CreateBulkTaskAssignRequest createBulkTaskAssignRequest) {
        try {
            this.bulkTaskAssignValidator.validateSaveBulkTaskAssign(createBulkTaskAssignRequest);
            final BulkTaskAssign bulkTaskAssign = BulkTaskAssign.create(createBulkTaskAssignRequest);
            this.bulkTaskAssignRepository.saveAndFlush(bulkTaskAssign);
            return Response.of(Long.valueOf(bulkTaskAssign.getId()));
        } catch (DataIntegrityViolationException e) {
            log.error("Error creating BulkTaskAssign: {}", e.getMessage(), e);
            throw new EQAS_SAN_ApplicationException("Error creating BulkTaskAssign:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateBulkTaskAssign(Integer id, UpdateBulkTaskAssignRequest updateBulkTaskAssignRequest) {
        try {
            this.bulkTaskAssignValidator.validateUpdateBulkTaskAssign(updateBulkTaskAssignRequest);
            final BulkTaskAssign bulkTaskAssign = this.bulkTaskAssignWrapper.findOneWithNotFoundDetection(id);
            bulkTaskAssign.update(updateBulkTaskAssignRequest);
            this.bulkTaskAssignRepository.saveAndFlush(bulkTaskAssign);
            return Response.of(Long.valueOf(bulkTaskAssign.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response deactive(Integer id, Integer euid) {
        try {
            BulkTaskAssign BulkTaskAssign = this.bulkTaskAssignWrapper.findOneWithNotFoundDetection(id);
            BulkTaskAssign.setEuid(euid);
            BulkTaskAssign.setStatus(Status.DELETE);
            BulkTaskAssign.setUpdatedAt(LocalDateTime.now());
            return Response.of(Long.valueOf(BulkTaskAssign.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<HitRecordData> fetchAllRecordData(Integer searchId) {
        final HitRecordRowMapper rowMapper = new HitRecordRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE hit.searchId = ? ";
        Qry = Qry + whereClause;
        final List<HitRecordData> hitRecordDataList = jdbcTemplate.query(Qry, hitRecordRowMapper,
                new Object[]{searchId}
        );
        return hitRecordDataList;
    }

    @Override
    @Transactional
    public List<BulkTaskAssignData> fetchAllBulkTaskAssignData() {
        final BulkTaskAssignRowMapper rowMapper = new BulkTaskAssignRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE a.isTaskAssigned = 1 AND a.status = 'A' ";
        Qry = Qry + whereClause;
        final List<BulkTaskAssignData> bulkTaskAssignDataList = jdbcTemplate.query(Qry, bulkTaskAssignRowMapper,
                new Object[]{}
        );
        return bulkTaskAssignDataList;
    }



    @Override
    @Transactional
    public Response createBulkAssign(CreateBulkTaskAssignRequest createBulkTaskAssignRequest) {
        try {
            final BulkTaskAssign bulkTaskAssign = BulkTaskAssign.create(createBulkTaskAssignRequest);
            this.bulkTaskAssignRepository.saveAndFlush(bulkTaskAssign);

            List<Integer> hitIds = this.excelReadService.fetchHitIds(createBulkTaskAssignRequest.getSearchId());

            for (BulkAssignMappingData bulkAssignMappingData : createBulkTaskAssignRequest.getOfacDataList()) {
                RecordDTO recordDTO = createRecordDTO(bulkAssignMappingData, createBulkTaskAssignRequest.getSearchId());
                for (Integer hitId : hitIds) {
                    recordDTO.setHitId(hitId);
                    recordDTO.setFileType(bulkAssignMappingData.getFiletype());
                    createAndSaveBulkAssignMapping(recordDTO, bulkTaskAssign.getId());
                }
            }
            return Response.of(Long.valueOf(bulkTaskAssign.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException("Error creating BulkTaskAssign: " + e.getMessage());
        }
    }

    private RecordDTO createRecordDTO(BulkAssignMappingData bulkAssignMappingData, Integer searchId) {
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setIds(bulkAssignMappingData.getHitId());
        recordDTO.setFileType(bulkAssignMappingData.getFiletype());
        recordDTO.setSearchId(searchId);
        return recordDTO;
    }


    private void createAndSaveBulkAssignMapping(RecordDTO recordDTO, Integer bulkTaskAssignId) {
        CreateBulkAssignMappingRequest createBulkAssignMappingRequest = new CreateBulkAssignMappingRequest();
        createBulkAssignMappingRequest.setBulkAssignId(bulkTaskAssignId);
        createBulkAssignMappingRequest.setSearchId(recordDTO.getSearchId());
        createBulkAssignMappingRequest.setHitId(recordDTO.getHitId());
        createBulkAssignMappingRequest.setFileType(recordDTO.getFileType());
        createBulkAssignMapping(createBulkAssignMappingRequest);
    }

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
}
