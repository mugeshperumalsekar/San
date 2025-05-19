package com.ponsun.san.sanStrfile.api;

import com.ponsun.san.sanStrfile.data.SanStrFileData;
import com.ponsun.san.sanStrfile.service.SanStrFileWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/SanSTRfile")
public class SanStrFile {
    private final SanStrFileWriteService sanStrFileWriteService;


    @GetMapping("/fetch-all")
    public List<SanStrFileData> getStrFileCount(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return sanStrFileWriteService.fetchAllLStr(startDate, endDate);
    }

}