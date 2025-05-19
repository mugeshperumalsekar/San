package com.ponsun.san.bulkTaskAssign.domain;

import com.ponsun.san.bulkTaskAssign.request.AbstractBulkTaskAssignRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BulkTaskAssignWrapper extends AbstractBulkTaskAssignRequest {
    private final BulkTaskAssignRepository BulkTaskAssignRepository;

    @Transactional
    public BulkTaskAssign findOneWithNotFoundDetection(final Integer id){
        return this.BulkTaskAssignRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BulkTaskAssign Not found " + id));
    }
    @Override
    public String toString(){ return super.toString();}
}
