package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass3;

import com.ponsun.san.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntityClass3RepositoryWrapper {

    private final EntityClass3Repository entityClass3Repository;

    @Transactional(readOnly = true)
    public EntityClass3 findOneWithNotFoundDetection(final Integer id) {
        return this.entityClass3Repository.findById(id).orElseThrow(() -> new NotFoundException("EntityClass3 Not found " + id));
    }

    @Transactional(readOnly = true)
    public EntityClass3 findOneWithDataId(final String dataId) {
        return this.entityClass3Repository.findOneWithDataId(dataId).orElse(null);
    }
}
