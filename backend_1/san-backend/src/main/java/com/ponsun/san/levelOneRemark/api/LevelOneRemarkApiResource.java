package com.ponsun.san.levelOneRemark.api;

import com.ponsun.san.levelOneRemark.data.LevelOneRemarkData;
import com.ponsun.san.levelOneRemark.services.LevelOneRemarkWritePlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/LevelOneRemark")
public class LevelOneRemarkApiResource {
    private final LevelOneRemarkWritePlatformService levelOneRemarkWritePlatformService;

    @GetMapping
    public List<LevelOneRemarkData> fetchAll(@RequestParam Integer criminalId, @RequestParam Integer hitdataId,@RequestParam Integer levelId,@RequestParam Integer statusId){
        return this.levelOneRemarkWritePlatformService.fetchAllLevelOneRemark(criminalId,hitdataId,levelId,statusId);
    }

    @GetMapping("/RIF")
    public List<LevelOneRemarkData> fetchAllRIF(@RequestParam Integer levelId,@RequestParam Integer statusId){
        return this.levelOneRemarkWritePlatformService.fetchAllRIF(levelId,statusId);
    }
}
