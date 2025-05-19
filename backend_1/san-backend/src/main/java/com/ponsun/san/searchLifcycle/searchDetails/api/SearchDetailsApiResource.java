package com.ponsun.san.searchLifcycle.searchDetails.api;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.searchDetails.domain.SearchDetails;
import com.ponsun.san.searchLifcycle.searchDetails.request.CreateSearchDetailsRequest;
import com.ponsun.san.searchLifcycle.searchDetails.request.UpdateSearchDetailsRequest;
import com.ponsun.san.searchLifcycle.searchDetails.services.SearchDetailsReadPlatformService;
import com.ponsun.san.searchLifcycle.searchDetails.services.SearchDetailsWritePlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/searchDetails")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="SearchDetailsApiResource")
public class SearchDetailsApiResource {

    private final SearchDetailsWritePlatformService searchDetailsWritePlatformService;
    private final SearchDetailsReadPlatformService searchDetailsReadPlatformService;

    @PostMapping("/createSearchDetails")
    public Response saveSearchDetails(@RequestBody CreateSearchDetailsRequest createSearchDetailsRequest){
        Response response = this.searchDetailsWritePlatformService.createSearchDetails(createSearchDetailsRequest);
        return response;
    }

    @GetMapping
    public List<SearchDetails> fetchAll() {return this.searchDetailsReadPlatformService.fetchAllSearchDetails();}

    @GetMapping("/{id}")
    public SearchDetails fetchSearchDetailsById(@PathVariable(name="id")Integer id){
        return this.searchDetailsReadPlatformService.fetchSearchDetailsById(id);
    }

    @PutMapping("/{id}")
    public Response updateSearchDetails(@PathVariable Integer id, @RequestBody UpdateSearchDetailsRequest updateSearchDetailsRequest){
        Response response = this.searchDetailsWritePlatformService.updateSearchDetails(id,updateSearchDetailsRequest);
        return response;
    }
}
