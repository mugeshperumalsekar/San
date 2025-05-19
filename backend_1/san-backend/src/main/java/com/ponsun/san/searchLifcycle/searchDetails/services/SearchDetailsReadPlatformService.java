package com.ponsun.san.searchLifcycle.searchDetails.services;

import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetails;

import java.util.List;

public interface SearchDetailsReadPlatformService {
    SearchDetails fetchSearchDetailsById(Integer id);
    List<SearchDetails> fetchAllSearchDetails();

}
