package com.ponsun.san.master.levelMapping.data;

import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.master.levelMapping.request.CreateLevelMappingRequest;
import com.ponsun.san.master.levelMapping.request.UpdateLevelMappingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LevelMappingValidator {

    public void validateSaveLevel(final CreateLevelMappingRequest request) {
        if (request.getLevelId() == null || request.getLevelId().equals("")) {
            throw new EQAS_SAN_ApplicationException("Level parameter required");
        }
    }
    public void validateUpdateLevel(final UpdateLevelMappingRequest request) {
        if (request.getLevelId() == null || request.getLevelId().equals("")) {
            throw new EQAS_SAN_ApplicationException("Level parameter required");
        }
    }
}
