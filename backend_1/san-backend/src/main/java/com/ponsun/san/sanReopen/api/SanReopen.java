package com.ponsun.san.sanReopen.api;

import com.ponsun.san.sanReopen.data.SanReopenData;
import com.ponsun.san.sanReopen.service.SanReopenDataWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/SanReopen")
public class SanReopen {

    private final SanReopenDataWriteService sanReopenDataWriteService;


@GetMapping
public List<SanReopenData> fetchAll(
        @RequestParam List<Integer> uid,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate
) {
    return sanReopenDataWriteService.fetchAllLReopen(uid, startDate, endDate);
}
}
