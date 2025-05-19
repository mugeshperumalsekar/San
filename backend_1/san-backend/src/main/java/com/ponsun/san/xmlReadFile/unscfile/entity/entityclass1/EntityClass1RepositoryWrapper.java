package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1;

import com.ponsun.san.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntityClass1RepositoryWrapper {

    private final EntityClass1Repository entityClass1Repository;

    @Transactional(readOnly = true)
    public EntityClass1 findOneWithNotFoundDetection(final Integer id) {
        return this.entityClass1Repository.findById(id).orElseThrow(() -> new NotFoundException("EntityClass1 Not found " + id));
    }

    @Transactional(readOnly = true)
    public EntityClass1 findOneWithDataId(final String dataId) {
        return this.entityClass1Repository.findOneWithDataId(dataId).orElse(null);
    }

    public int getColumnCount(String tableName) {
        return this.entityClass1Repository.getColumnCount(tableName);
    }
}
