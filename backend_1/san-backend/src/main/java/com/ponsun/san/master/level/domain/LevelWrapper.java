package com.ponsun.san.master.level.domain;

import com.ponsun.san.master.level.request.AbstractLevelRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LevelWrapper extends AbstractLevelRequest {

    private final LevelRepository levelRepository;
    @Transactional
    public Level findOneWithNotFoundDetection(final Integer id) {
        return this.levelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Level Not found " + id));
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

