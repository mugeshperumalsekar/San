package com.ponsun.san.searchLifcycle.search.services;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.search.data.SearchData;
import com.ponsun.san.searchLifcycle.search.request.CreateSearchRequest;
import com.ponsun.san.searchLifcycle.search.request.UpdateSearchRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SearchWritePlatformService {

    Response createSearch(CreateSearchRequest createSearchRequest);
    Response updateSearch(Integer id, UpdateSearchRequest updateSearchRequest);
    Response blockSearch(Integer id);
    Response unblockSearch(Integer id);
    List<SearchData> fetchByIsBulkSearch(Integer isBulkSearch);


    List<SearchData> fetchIsBulkSearch();

    List<SearchData> fetchAllSearch();
}
