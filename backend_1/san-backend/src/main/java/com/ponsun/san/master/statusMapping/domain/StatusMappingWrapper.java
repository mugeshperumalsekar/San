package com.ponsun.san.master.statusMapping.domain;

import com.ponsun.san.master.statusMapping.request.AbstractStatusMappingRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatusMappingWrapper extends AbstractStatusMappingRequest {

    private final StatusMappingRepository StatusMappingRepository;
    @Transactional
    public StatusMapping findOneWithNotFoundDetection(final Integer id) {
        return this.StatusMappingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("StatusMapping Not found " + id));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

