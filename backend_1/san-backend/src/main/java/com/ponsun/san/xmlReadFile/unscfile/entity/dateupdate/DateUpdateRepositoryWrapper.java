package com.ponsun.san.xmlReadFile.unscfile.entity.dateupdate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DateUpdateRepositoryWrapper {
    private final DateUpdateRepository dateUpdateRepository;

    @Transactional(readOnly = true)
    public DateUpdate findOneWithDataId(final Integer dataId) {
        return this.dateUpdateRepository.findOneWithId(dataId).orElse(null);
    }
}
