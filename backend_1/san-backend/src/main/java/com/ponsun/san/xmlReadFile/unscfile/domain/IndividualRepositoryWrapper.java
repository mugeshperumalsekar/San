package com.ponsun.san.xmlReadFile.unscfile.domain;

import com.ponsun.san.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndividualRepositoryWrapper {


    private final IndividualRepository individualRepository;

    @Transactional(readOnly = true)
    public Individual findOneWithNotFoundDetection(final Integer id) {
        return this.individualRepository.findById(id).orElseThrow(() -> new NotFoundException("Individual Not found " + id));
    }

    @Transactional(readOnly = true)
    public Individual findOneWithDataId(final String dataId) {
        return this.individualRepository.findOneWithDataId(dataId).orElse(null);
    }

}
