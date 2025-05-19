package com.ponsun.san.searchLifcycle.searchDetails.domain;

import com.ponsun.san.searchLifcycle.searchDetails.request.AbstractSearchDetailsBaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchDetailsRepositoryWrapper extends AbstractSearchDetailsBaseRequest {
    private final SearchDetailsRepository searchDetailsRepository;

    @Transactional
    public SearchDetails findOneWithNotFoundDetection(final Integer id){
        return this.searchDetailsRepository.findById(id).orElseThrow(()->new EntityNotFoundException("SearchDetails Not found "+ id));
    }
    @Override
    public String toString(){return super.toString();}
}
