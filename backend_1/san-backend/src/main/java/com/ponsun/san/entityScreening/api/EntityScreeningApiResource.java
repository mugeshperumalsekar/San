package com.ponsun.san.entityScreening.api;


import com.ponsun.san.entityScreening.data.EntityScreeningData;
import com.ponsun.san.entityScreening.services.EntityScreeningWritePlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/EntityScreening")
public class EntityScreeningApiResource {
    private final EntityScreeningWritePlatformService entityScreeningWritePlatformService;

    @GetMapping
    public List<EntityScreeningData> fetchAll(@RequestParam Integer kycId){
        return this.entityScreeningWritePlatformService.fetchAllEntityScreening(kycId);
    }
}
