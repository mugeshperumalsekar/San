package com.ponsun.san.searchLifcycle.searchDetails.services;

import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetails;
import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetailsRepository;
import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetailsRepositoryWrapper;
import com.ponsun.san.searchLifcycle.searchDetails.request.CreateSearchDetailsRequest;
import com.ponsun.san.searchLifcycle.searchDetails.request.UpdateSearchDetailsRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchDetailsWritePlatformServiceImpl implements SearchDetailsWritePlatformService{
    private final SearchDetailsRepository searchDetailsRepository;
    private final SearchDetailsRepositoryWrapper searchDetailsRepositoryWrapper;

    @Override
    @Transactional
    public Response createSearchDetails(CreateSearchDetailsRequest createSearchDetailsRequest){
        try{
            SearchDetails searchDetails = SearchDetails.create(createSearchDetailsRequest);
            searchDetails = this.searchDetailsRepository.saveAndFlush(searchDetails);
            return Response.of(searchDetails.getId());
        } catch(DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }
    @Override
    @Transactional
    public Response updateSearchDetails(Integer id, UpdateSearchDetailsRequest updateSearchDetailsRequest){
        try{
            final SearchDetails searchDetails = this.searchDetailsRepositoryWrapper.findOneWithNotFoundDetection(id);
            searchDetails.update(updateSearchDetailsRequest);
            this.searchDetailsRepository.saveAndFlush(searchDetails);
            return Response.of(Long.valueOf(searchDetails.getId()));
        } catch(DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }
}

