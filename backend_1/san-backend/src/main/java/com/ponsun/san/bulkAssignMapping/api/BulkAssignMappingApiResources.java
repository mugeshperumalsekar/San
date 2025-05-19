package com.ponsun.san.bulkAssignMapping.api;


import com.ponsun.san.bulkAssignMapping.data.BulkAssignMappingData;
import com.ponsun.san.bulkAssignMapping.domain.BulkAssignMapping;
import com.ponsun.san.bulkAssignMapping.request.CreateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.request.UpdateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.services.BulkAssignMappingReadService;
import com.ponsun.san.bulkAssignMapping.services.BulkAssignMappingWriteService;
import com.ponsun.san.infrastructure.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/BulkAssignMapping")
@Tag(name = "BulkAssignMappingApiResource")

public class BulkAssignMappingApiResources {
    private final BulkAssignMappingWriteService bulkAssignMappingWriteService;
    private final BulkAssignMappingReadService bulkAssignMappingReadService;

    @PostMapping("/createBulkAssignMapping")
    public Response saveBulkAssignMapping(@RequestBody CreateBulkAssignMappingRequest createBulkAssignMappingRequest) {
        log.debug("START saveBulkAssignMapping request body {}", createBulkAssignMappingRequest);
        Response response = this.bulkAssignMappingWriteService.createBulkAssignMapping(createBulkAssignMappingRequest);
        log.debug("START saveBulkAssignMapping response", response);
        return response;
    }

    @GetMapping
    public List<BulkAssignMapping> fetchAll() {
        return this.bulkAssignMappingReadService.fetchAllBulkAssignMapping();
    }

    @GetMapping("/{id}")
    public BulkAssignMapping fetchAllBulkAssignMappingById(@PathVariable Integer id) {
        return this.bulkAssignMappingReadService.fetchBulkAssignMappingById(id);
    }

    @PutMapping("/{id}")
    public Response updateBulkAssignMapping(@PathVariable Integer id, @RequestBody UpdateBulkAssignMappingRequest updateBulkAssignMappingRequest) {
        log.debug("START updateBulkAssignMapping request body {}", updateBulkAssignMappingRequest);
        Response response = this.bulkAssignMappingWriteService.updateBulkAssignMapping(id, updateBulkAssignMappingRequest);
        log.debug("START updateBulkAssignMapping response", response);
        return response;
    }

    @PutMapping("/deactive/{id}")
    public Response deactive(@PathVariable Integer id, Integer euid) {
        Response response = this.bulkAssignMappingWriteService.deactive(id, euid);
        return response;
    }

    @GetMapping("/records")
    public List<BulkAssignMappingData> fetchAllRecordData(@RequestParam Integer searchId) {
        return this.bulkAssignMappingWriteService.fetchAllRecordData(searchId);
    }

    @GetMapping("/search/{searchId}")
    public List<BulkAssignMappingData> getHitRecords(@PathVariable Integer searchId) {
        return bulkAssignMappingWriteService.getHitRecordsBySearchId(searchId);
    }
}
