package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass4;

import com.ponsun.san.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class EntityClass4RepositoryWrapper {
    private final EntityClass4Repository entityClass4Repository;

    @Transactional(readOnly = true)
    public EntityClass4 findOneWithNotFoundDetection(final Integer id) {
        return this.entityClass4Repository.findById(id).orElseThrow(() -> new NotFoundException("EntityClass4 Not found " + id));
    }

    @Transactional(readOnly = true)
    public EntityClass4 findOneWithDataId(final String dataId) {
        return this.entityClass4Repository.findOneWithDataId(dataId).orElse(null);
    }
    
}
