package com.ponsun.san.searchLifcycle.RemarkDetails.api;

import com.ponsun.san.searchLifcycle.RemarkDetails.data.RemarkDetailsData;
import com.ponsun.san.searchLifcycle.RemarkDetails.services.RemarkDetailsReadPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/RemarksDetails")
public class RemarkDetailsApiResource {
    private final RemarkDetailsReadPlatformService remarkDetailsReadPlatformService;

    @GetMapping
    public List<RemarkDetailsData> fetchAll(@RequestParam Integer hitdataId){
        return this.remarkDetailsReadPlatformService.fetchAllRemark(hitdataId);
    }
}
