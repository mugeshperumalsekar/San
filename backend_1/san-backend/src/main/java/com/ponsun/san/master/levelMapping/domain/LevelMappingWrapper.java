package com.ponsun.san.master.levelMapping.domain;

import com.ponsun.san.master.levelMapping.request.AbstractLevelMappingRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LevelMappingWrapper extends AbstractLevelMappingRequest {

    private final LevelMappingRepository LevelMappingRepository;
    @Transactional
    public LevelMapping findOneWithNotFoundDetection(final Integer id) {
        return this.LevelMappingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LevelMapping Not found " + id));
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

