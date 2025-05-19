package com.ponsun.san.master.LevelOne.api;



import com.ponsun.san.master.LevelOne.data.LevelOneData;
import com.ponsun.san.master.LevelOne.data.StatusDataMapping;
import com.ponsun.san.master.LevelOne.service.LevelOneReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/LevelOne")
@CrossOrigin(origins = "http://localhost:3000")
public class LevelOneApiResources {

    private final LevelOneReadService levelOneReadService;


    @GetMapping("/levelId")
    public List<LevelOneData> fetchLevelOneData(@RequestParam Integer levelId) {
        return this.levelOneReadService.fetchLevelOneData(levelId);
    }

    @GetMapping("/{levelId}/status")
    public List<StatusDataMapping> fetchStatusMappingData(@RequestParam Integer levelId) {
        return this.levelOneReadService.fetchStatusMappingData(levelId);
    }
}
