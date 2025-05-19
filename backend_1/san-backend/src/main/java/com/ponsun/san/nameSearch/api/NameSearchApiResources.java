package com.ponsun.san.nameSearch.api;


import com.ponsun.san.nameSearch.data.NameSearchData;
import com.ponsun.san.nameSearch.services.NameSearchReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/NameSearch")
@Tag(name = "NameSearchApiResources")
public class NameSearchApiResources {

    private final NameSearchReadService nameSearchReadService;
    @GetMapping("/KycFormNameSearch/{kycId}")
    public List<NameSearchData> fetchAllNameSearch(@RequestParam Integer kycId) {
        return this.nameSearchReadService.fetchAllNameSearch(kycId);
    }
}
