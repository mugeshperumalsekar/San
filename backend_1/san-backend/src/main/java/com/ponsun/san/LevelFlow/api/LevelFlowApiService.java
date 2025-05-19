package com.ponsun.san.LevelFlow.api;

import com.ponsun.san.LevelFlow.data.LevelFlowData;
import com.ponsun.san.LevelFlow.data.LevelFlowWriteData;
import com.ponsun.san.LevelFlow.service.LevelFlowDataWriteService;
import com.ponsun.san.LevelFlow.service.LevelFlowReadService;
import com.ponsun.san.infrastructure.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/LevelFlowApiService")
public class LevelFlowApiService {
    private final LevelFlowReadService levelFlowReadService;
    private final LevelFlowDataWriteService levelFlowDataWriteService;

    @GetMapping
    public List<LevelFlowData> fetchAll(@RequestParam Integer levelId,@RequestParam Integer statusId){
        return this.levelFlowReadService.fetchAllLevelFlow(levelId,statusId);
    }

    @PostMapping("/CreateLevelRequest")
    public Response saveLevel(@RequestBody LevelFlowWriteData levelFlowWriteData){
        Response response = this.levelFlowDataWriteService.handleReview(levelFlowWriteData);
        return response;
    }
}

