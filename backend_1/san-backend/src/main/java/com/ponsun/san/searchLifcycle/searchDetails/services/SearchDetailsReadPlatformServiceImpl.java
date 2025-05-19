package com.ponsun.san.searchLifcycle.searchDetails.services;

import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetails;
import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchDetailsReadPlatformServiceImpl implements SearchDetailsReadPlatformService{
    private final SearchDetailsRepository searchDetailsRepository;
    @Override
    public SearchDetails fetchSearchDetailsById(Integer id){return this.searchDetailsRepository.findById(id).get();}

    @Override
    public List<SearchDetails> fetchAllSearchDetails() { return this.searchDetailsRepository.findAll();}

}
