package com.ponsun.san.bulkTaskAssign.api;


import com.ponsun.san.bulkTaskAssign.data.BulkTaskAssignData;
import com.ponsun.san.bulkTaskAssign.domain.BulkTaskAssign;
import com.ponsun.san.bulkTaskAssign.request.CreateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.request.UpdateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.services.BulkTaskAssignReadService;
import com.ponsun.san.bulkTaskAssign.services.BulkTaskAssignWriteService;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitrecord.data.HitRecordData;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/BulkTaskAssign")
@Tag(name = "BulkTaskAssignApiResource")

public class BulkTaskAssignApiResources {
    private final BulkTaskAssignWriteService bulkTaskAssignWriteService;
    private final BulkTaskAssignReadService bulkTaskAssignReadService;

    @PostMapping("/createBulkTaskAssign")
    public Response saveBulkTaskAssign(@RequestBody CreateBulkTaskAssignRequest createBulkTaskAssignRequest) {
        log.debug("START saveBulkTaskAssign request body {}", createBulkTaskAssignRequest);
        Response response = this.bulkTaskAssignWriteService.createBulkTaskAssign(createBulkTaskAssignRequest);
        log.debug("START saveBulkTaskAssign response", response);
        return response;
    }

    @GetMapping
    public List<BulkTaskAssign> fetchAll() {
        return this.bulkTaskAssignReadService.fetchAllBulkTaskAssign();
    }

//    @GetMapping("/{id}")
//    public BulkTaskAssign fetchAllBulkTaskAssignById(@PathVariable Integer id) {
//        return this.bulkTaskAssignReadService.fetchBulkTaskAssignById(id);
//    }

    @PutMapping("/{id}")
    public Response updateBulkTaskAssign(@PathVariable Integer id, @RequestBody UpdateBulkTaskAssignRequest updateBulkTaskAssignRequest) {
        log.debug("START updateBulkTaskAssign request body {}", updateBulkTaskAssignRequest);
        Response response = this.bulkTaskAssignWriteService.updateBulkTaskAssign(id, updateBulkTaskAssignRequest);
        log.debug("START updateBulkTaskAssign response", response);
        return response;
    }

    @PutMapping("/deactive/{id}")
    public Response deactive(@PathVariable Integer id, Integer euid) {
        Response response = this.bulkTaskAssignWriteService.deactive(id, euid);
        return response;
    }

    @GetMapping("/{isTaskAssigned}/Active")
    public List<BulkTaskAssignData> fetchAllBulkTaskAssignData() {
        return this.bulkTaskAssignWriteService.fetchAllBulkTaskAssignData();
    }

    @GetMapping("/{searchId}")
    public List<HitRecordData> fetchAllRecordData(@RequestParam Integer searchId) {
        return this.bulkTaskAssignWriteService.fetchAllRecordData(searchId);
    }

    @PostMapping("/createBulkAssign")
    public Response saveBulkAssign(@RequestBody CreateBulkTaskAssignRequest createBulkTaskAssignRequest) {
        log.debug("START saveBulkTaskAssign request body {}", createBulkTaskAssignRequest);
        Response response = this.bulkTaskAssignWriteService.createBulkAssign(createBulkTaskAssignRequest);
        log.debug("START saveBulkTaskAssign response", response);
        return response;
    }

}
