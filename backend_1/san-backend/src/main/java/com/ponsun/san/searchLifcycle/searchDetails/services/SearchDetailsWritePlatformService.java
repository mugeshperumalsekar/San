package com.ponsun.san.searchLifcycle.searchDetails.services;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.searchDetails.request.CreateSearchDetailsRequest;
import com.ponsun.san.searchLifcycle.searchDetails.request.UpdateSearchDetailsRequest;

public interface SearchDetailsWritePlatformService {
    Response createSearchDetails(CreateSearchDetailsRequest createSearchDetailsRequest);
    Response updateSearchDetails(Integer id, UpdateSearchDetailsRequest updateSearchDetailsRequest);
}
