package com.ponsun.san.master.level.api;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.level.domain.Level;
import com.ponsun.san.master.level.request.CreateLevelRequest;
import com.ponsun.san.master.level.request.UpdateLevelRequest;
import com.ponsun.san.master.level.services.LevelReadService;
import com.ponsun.san.master.level.services.LevelWriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/Level")
@Tag(name = "LevelApiResources")
public class LevelApiResources {
    private final LevelWriteService levelWriteService;
    private final LevelReadService levelReadService;
    @PostMapping("/CreateLevelRequest")
    public Response saveLevel(@RequestBody CreateLevelRequest createLevelRequest){
        Response response = this.levelWriteService.createLevel(createLevelRequest);
        return response;
    }
    @GetMapping
    public List<Level> fetchAll(){
        return this.levelReadService.fetchAllLevel();
    }

    @GetMapping("/{id}")
    public Level fetchLevelById(@PathVariable(name = "id")Integer id){
        return this.levelReadService.fetchLevelById(id);
    }

    @PutMapping("/{id}")
    public Response updateLevel(@PathVariable Integer id, @RequestBody UpdateLevelRequest updateLevelRequest){
        Response response = this.levelWriteService.updateLevel(id,updateLevelRequest);
        return  response;
    }
}
