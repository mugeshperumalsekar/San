package com.ponsun.san.master.statusMapping.services;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.statusMapping.data.StatusMappingData;
import com.ponsun.san.master.statusMapping.data.StatusMappingValidator;
import com.ponsun.san.master.statusMapping.domain.StatusMapping;
import com.ponsun.san.master.statusMapping.domain.StatusMappingRepository;
import com.ponsun.san.master.statusMapping.domain.StatusMappingWrapper;
import com.ponsun.san.master.statusMapping.request.CreateStatusMappingRequest;
import com.ponsun.san.master.statusMapping.request.UpdateStatusMappingRequest;
import com.ponsun.san.master.statusMapping.rowmapper.StatusMappingRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusMappingWriteServiceImpl implements StatusMappingWriteService {
    private final StatusMappingRepository statusMappingRepository;
    private final StatusMappingWrapper statusMappingWrapper;
    private final StatusMappingValidator statusMappingValidator;
    private final JdbcTemplate jdbcTemplate;
    private final StatusMappingRowMapper statusMappingRowMapper;

    @Override
    @Transactional
    public Response createStatusMapping(CreateStatusMappingRequest createStatusMappingRequest) {
        try {
            Integer levelId = createStatusMappingRequest.getLevelId();
            Integer statusId = createStatusMappingRequest.getStatusId();
            if (statusMappingRepository.existsByLevelIdAndStatusId(levelId, statusId)) {
                throw new EQAS_SAN_ApplicationException("Level and Status exists");
            }
            this.statusMappingValidator.validateSaveStatus(createStatusMappingRequest);
            StatusMapping statusMapping = StatusMapping.create(createStatusMappingRequest);
            statusMapping = this.statusMappingRepository.saveAndFlush(statusMapping);
            return Response.of(statusMapping.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateStatusMapping(Integer id, UpdateStatusMappingRequest updateStatusMappingRequest) {
        try {
            this.statusMappingValidator.validateUpdateStatus(updateStatusMappingRequest);
            final StatusMapping StatusMapping = this.statusMappingWrapper.findOneWithNotFoundDetection(id);
            StatusMapping.update(updateStatusMappingRequest);
            this.statusMappingRepository.saveAndFlush(StatusMapping);
            return Response.of(Long.valueOf(StatusMapping.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response blockStatusMapping(Integer id) {
        try {
            final StatusMapping statusMapping = this.statusMappingWrapper.findOneWithNotFoundDetection(id);
            statusMapping.setStatus(Status.DELETE);
            statusMapping.setUpdatedAt(LocalDateTime.now());
            this.statusMappingRepository.saveAndFlush(statusMapping);
            return Response.of(Long.valueOf(id));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response unblockStatusMapping(Integer id) {
        try {
            final StatusMapping statusMapping = this.statusMappingWrapper.findOneWithNotFoundDetection(id);
            statusMapping.setStatus(Status.ACTIVE);
            statusMapping.setUpdatedAt(LocalDateTime.now());
            this.statusMappingRepository.saveAndFlush(statusMapping);
            return Response.of(Long.valueOf(id));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public Response createOrUpdateStatusMapping(Integer levelId, Integer statusId, CreateStatusMappingRequest createStatusMappingRequest) {
        try {
            List<StatusMapping> existingStatusMappings = statusMappingRepository.findByLevelIdAndStatusId(levelId, statusId);

            for (StatusMapping statusMappingToBlock : existingStatusMappings) {
                statusMappingToBlock.setStatus(Status.DELETE);
                statusMappingToBlock.setUpdatedAt(LocalDateTime.now());
            }
            statusMappingRepository.saveAll(existingStatusMappings);

            this.statusMappingValidator.validateSaveStatus(createStatusMappingRequest);
            StatusMapping newStatusMapping = StatusMapping.create(createStatusMappingRequest);
            newStatusMapping = this.statusMappingRepository.saveAndFlush(newStatusMapping);

            return Response.of(newStatusMapping.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }


    @Override
    public List<StatusMappingData> fetchStatusMappingData(Integer levelId, Integer statusId) {
        final StatusMappingRowMapper rowMapper = new StatusMappingRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE  a.levelId = ? AND a.statusId = ? ";
        Qry = Qry + whereClause;
        final List<StatusMappingData> statusMappingDataList = jdbcTemplate.query(Qry, statusMappingRowMapper,
                new Object[]{levelId , statusId}
        );
        return statusMappingDataList;
    }
}

