package com.ponsun.san.master.level.services;

import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.level.data.LevelValidator;
import com.ponsun.san.master.level.domain.Level;
import com.ponsun.san.master.level.domain.LevelRepository;
import com.ponsun.san.master.level.domain.LevelWrapper;
import com.ponsun.san.master.level.request.CreateLevelRequest;
import com.ponsun.san.master.level.request.UpdateLevelRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j

public class LevelWriteServiceImpl implements LevelWriteService {
    private final LevelRepository levelRepository;
    private final LevelWrapper levelWrapper;
    private final LevelValidator levelValidator;

    @Override
    @Transactional
    public Response createLevel(CreateLevelRequest createLevelRequest) {
        try {
            this.levelValidator.validateSaveLevelData(createLevelRequest);
            Level level = Level.create(createLevelRequest);
            level = this.levelRepository.saveAndFlush(level);
            return Response.of(level.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateLevel(Integer id, UpdateLevelRequest updateLevelRequest) {
        try {
            this.levelValidator.validateUpdateLevelData(updateLevelRequest);
            final Level Level = this.levelWrapper.findOneWithNotFoundDetection(id);
            Level.update(updateLevelRequest);
            this.levelRepository.saveAndFlush(Level);
            return Response.of(Long.valueOf(Level.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }
}

