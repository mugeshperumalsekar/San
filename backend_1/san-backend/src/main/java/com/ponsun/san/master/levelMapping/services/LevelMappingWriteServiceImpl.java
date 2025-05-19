package com.ponsun.san.master.levelMapping.services;
import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.levelMapping.data.LevelMappingData;
import com.ponsun.san.master.levelMapping.data.LevelMappingValidator;
import com.ponsun.san.master.levelMapping.domain.LevelMapping;
import com.ponsun.san.master.levelMapping.domain.LevelMappingRepository;
import com.ponsun.san.master.levelMapping.domain.LevelMappingWrapper;
import com.ponsun.san.master.levelMapping.request.CreateLevelMappingRequest;
import com.ponsun.san.master.levelMapping.request.UpdateLevelMappingRequest;
import com.ponsun.san.master.levelMapping.rowmapper.LevelMappingRowMapper;
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
public class LevelMappingWriteServiceImpl implements LevelMappingWriteService {
    private final LevelMappingRepository levelMappingRepository;
    private final LevelMappingWrapper levelMappingWrapper;
    private final LevelMappingValidator levelMappingValidator;
    private final LevelMappingRowMapper levelMappingRowMapper;
    private final JdbcTemplate jdbcTemplate;


    @Override
    @Transactional
    public Response createLevelMapping(CreateLevelMappingRequest createLevelMappingRequest) {
        try {
            Integer levelId = createLevelMappingRequest.getLevelId();
            Integer LevelId = createLevelMappingRequest.getLevelId();
            if (levelMappingRepository.existsByLevelIdAndStatusId(levelId, LevelId)) {
                throw new EQAS_SAN_ApplicationException("Level and Level exists");
            }
            this.levelMappingValidator.validateSaveLevel(createLevelMappingRequest);
            LevelMapping levelMapping = LevelMapping.create(createLevelMappingRequest);
            levelMapping = this.levelMappingRepository.saveAndFlush(levelMapping);
            return Response.of(levelMapping.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateLevelMapping(Integer id, UpdateLevelMappingRequest updateLevelMappingRequest) {
        try {
            this.levelMappingValidator.validateUpdateLevel(updateLevelMappingRequest);
            final LevelMapping levelMapping = this.levelMappingWrapper.findOneWithNotFoundDetection(id);
            levelMapping.update(updateLevelMappingRequest);
            this.levelMappingRepository.saveAndFlush(levelMapping);
            return Response.of(Long.valueOf(levelMapping.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response blockLevelMapping(Integer id) {
        try {
            final LevelMapping levelMapping = this.levelMappingWrapper.findOneWithNotFoundDetection(id);
            levelMapping.setStatus(Status.DELETE);
            levelMapping.setUpdatedAt(LocalDateTime.now());
            this.levelMappingRepository.saveAndFlush(levelMapping);
            return Response.of(Long.valueOf(id));
        }catch (DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response unblockLevelMapping(Integer id) {
        try {
            final LevelMapping levelMapping = this.levelMappingWrapper.findOneWithNotFoundDetection(id);
            levelMapping.setStatus(Status.ACTIVE);
            levelMapping.setUpdatedAt(LocalDateTime.now());
            this.levelMappingRepository.saveAndFlush(levelMapping);
            return Response.of(Long.valueOf(id));
        }catch (DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public Response createOrUpdateLevelMapping(Integer levelId, Integer statusId, CreateLevelMappingRequest createLevelMappingRequest) {
        try {
            List<LevelMapping> existingLevelMappings = levelMappingRepository.findByLevelIdAndStatusId(levelId, statusId);

            for (LevelMapping LevelMappingToBlock : existingLevelMappings) {
                LevelMappingToBlock.setStatus(Status.DELETE);
                LevelMappingToBlock.setUpdatedAt(LocalDateTime.now());
            }
            levelMappingRepository.saveAll(existingLevelMappings);

            this.levelMappingValidator.validateSaveLevel(createLevelMappingRequest);
            LevelMapping newLevelMapping = LevelMapping.create(createLevelMappingRequest);
            newLevelMapping = this.levelMappingRepository.saveAndFlush(newLevelMapping);

            return Response.of(newLevelMapping.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }


    @Override
    public List<LevelMappingData> fetchLevelMappingData(Integer levelId, Integer statusId) {
        final LevelMappingRowMapper rowMapper = new LevelMappingRowMapper();
        String Qry = "SELECT " + rowMapper.tableSchema();
        String whereClause = " WHERE  a.levelId = ? AND a.statusId = ? ";
        Qry = Qry + whereClause;
        final List<LevelMappingData> LevelMappingDataList = jdbcTemplate.query(Qry, levelMappingRowMapper,
                new Object[]{levelId , statusId}
        );
        return LevelMappingDataList;
    }

}


