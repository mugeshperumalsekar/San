package com.ponsun.san.bulkAssignMapping.domain;

import com.ponsun.san.bulkAssignMapping.request.AbstractBulkAssignMappingRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BulkAssignMappingWrapper extends AbstractBulkAssignMappingRequest {
    private final BulkAssignMappingRepository BulkAssignMappingRepository;

    @Transactional
    public BulkAssignMapping findOneWithNotFoundDetection(final Integer id) {
        return this.BulkAssignMappingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BulkAssignMapping Not found " + id));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
