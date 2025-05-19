package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass2;


import com.ponsun.san.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class EntityClass2RepositoryWrapper {

    private final EntityClass2Repository entityClass2Repository;

    @Transactional(readOnly = true)
    public EntityClass2 findOneWithNotFoundDetection(final Integer id) {
        return this.entityClass2Repository.findById(id).orElseThrow(() -> new NotFoundException("EntityClass2 Not found " + id));
    }

    @Transactional(readOnly = true)
    public EntityClass2 findOneWithDataId(final String dataId) {
        return this.entityClass2Repository.findOneWithDataId(dataId).orElse(null);
    }

}
