package com.ponsun.san.master.statusMapping.api;

import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.master.statusMapping.data.StatusMappingData;
import com.ponsun.san.master.statusMapping.domain.StatusMapping;
import com.ponsun.san.master.statusMapping.request.CreateStatusMappingRequest;
import com.ponsun.san.master.statusMapping.services.StatusMappingReadService;
import com.ponsun.san.master.statusMapping.services.StatusMappingWriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/StatusMapping")
@Tag(name = "StatusMappingApiResources")
public class StatusMappingApiResources {
    private final StatusMappingWriteService statusMappingWriteService;
    private final StatusMappingReadService statusMappingReadService;

    @PostMapping("/CreateStatusMappingRequest")
    public Response saveStatusMapping(@RequestBody CreateStatusMappingRequest createStatusMappingRequest) {
        Response response = this.statusMappingWriteService.createStatusMapping(createStatusMappingRequest);
        return response;
    }
    @GetMapping
    public List<StatusMapping> fetchAll() {
        return this.statusMappingReadService.fetchAllStatusMapping();
    }

    @GetMapping("/{id}")
    public StatusMapping fetchStatusMappingById(@PathVariable(name = "id") Integer id) {
        return this.statusMappingReadService.fetchStatusMappingById(id);
    }


    @PutMapping("/{id}/block")
    public Response blockStatusMapping(@PathVariable Integer id) {
        Response response = this.statusMappingWriteService.blockStatusMapping(id);
        return response;
    }

    @PutMapping("/{id}/unblock")
    public Response unblockStatusMapping(@PathVariable Integer id) {
        Response response = this.statusMappingWriteService.unblockStatusMapping(id);
        return response;
    }


    @PutMapping("/{levelId}/{statusId}")
    public Response updateStatusMapping(@RequestParam Integer levelId , @RequestParam Integer statusId ,  @RequestBody CreateStatusMappingRequest createStatusMappingRequest) {
        Response response = this.statusMappingWriteService.createOrUpdateStatusMapping(levelId,statusId, createStatusMappingRequest);
        return response;
    }


    @GetMapping("/{levelId}/statusId")
    public List<StatusMappingData> fetchStatusMappingData(@RequestParam Integer levelId , @RequestParam Integer statusId) {
        return this.statusMappingWriteService.fetchStatusMappingData(levelId , statusId);
    }

    @GetMapping("active")
    public List<StatusMapping> fetchAllActive() {
        return this.statusMappingReadService.fetchAllActive();
    }
}
