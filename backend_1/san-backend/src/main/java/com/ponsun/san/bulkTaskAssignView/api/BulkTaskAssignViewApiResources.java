package com.ponsun.san.bulkTaskAssignView.api;



import com.ponsun.san.bulkTaskAssignView.data.BulkTaskAssignViewData;
import com.ponsun.san.bulkTaskAssignView.service.BulkTaskAssignViewReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/BulkTaskAssignView")
@Tag(name = "BulkTaskAssignViewApiResources")
public class BulkTaskAssignViewApiResources {

    private final BulkTaskAssignViewReadService bulkTaskAssignViewReadService;
    @GetMapping("/BulkTaskAssignView/{uid}")
    public List<BulkTaskAssignViewData> fetchAllBulkTaskAssignView(@RequestParam Integer uid) {
        return this.bulkTaskAssignViewReadService.fetchAllBulkTaskAssignView(uid);
    }
}
