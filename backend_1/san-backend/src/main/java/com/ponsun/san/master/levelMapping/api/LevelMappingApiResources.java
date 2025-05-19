package com.ponsun.san.master.levelMapping.api;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.levelMapping.data.LevelMappingData;
import com.ponsun.san.master.levelMapping.domain.LevelMapping;
import com.ponsun.san.master.levelMapping.request.CreateLevelMappingRequest;
import com.ponsun.san.master.levelMapping.services.LevelMappingReadService;
import com.ponsun.san.master.levelMapping.services.LevelMappingWriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/LevelMapping")
@Tag(name = "LevelMappingApiResources")
public class LevelMappingApiResources {
    private final LevelMappingWriteService levelMappingWriteService;
    private final LevelMappingReadService levelMappingReadService;

    @PostMapping("/CreateLevelMappingRequest")
    public Response saveLevelMapping(@RequestBody CreateLevelMappingRequest createLevelMappingRequest) {
        Response response = this.levelMappingWriteService.createLevelMapping(createLevelMappingRequest);
        return response;
    }

    @GetMapping
    public List<LevelMapping> fetchAll() {
        return this.levelMappingReadService.fetchAllLevelMapping();
    }

    @GetMapping("/{id}")
    public LevelMapping fetchLevelMappingById(@PathVariable(name = "id") Integer id) {
        return this.levelMappingReadService.fetchLevelMappingById(id);
    }

    @PutMapping("/{id}/block")
    public Response blockLevelMapping(@PathVariable Integer id) {
        Response response = this.levelMappingWriteService.blockLevelMapping(id);
        return response;
    }

    @PutMapping("/{id}/unblock")
    public Response unblockLevelMapping(@PathVariable Integer id) {
        Response response = this.levelMappingWriteService.unblockLevelMapping(id);
        return response;
    }

    @PutMapping("/{levelId}/{statusId}")
    public Response updateLevelMapping(@RequestParam Integer levelId, @RequestParam Integer statusId, @RequestBody CreateLevelMappingRequest createLevelMappingRequest) {
        Response response = this.levelMappingWriteService.createOrUpdateLevelMapping(levelId, statusId, createLevelMappingRequest);
        return response;
    }


    @GetMapping("/{levelId}/statusId")
    public List<LevelMappingData> fetchLevelMappingData(@RequestParam Integer levelId , @RequestParam Integer statusId) {
        return this.levelMappingWriteService.fetchLevelMappingData(levelId , statusId);
    }

    @GetMapping("active")
    public List<LevelMapping> fetchAllActive() {
        return this.levelMappingReadService.fetchAllActive();
    }
}
