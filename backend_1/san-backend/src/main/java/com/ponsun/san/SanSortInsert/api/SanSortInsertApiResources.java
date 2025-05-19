package com.ponsun.san.SanSortInsert.api;
import com.ponsun.san.SanSortInsert.service.SanSortInsertWriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/SanSortInsert")
@Tag(name = "SanSortInsertApiResources")
@CrossOrigin(origins = "http://localhost:3000")
public class SanSortInsertApiResources {
    private final SanSortInsertWriteService sanSortInsertWriteService;
    @PostMapping("/insertAddress")
    public String insertSortAddressData() {
        sanSortInsertWriteService.executeAddressInsertQuery();
        return "SortAddressData inserted successfully";
    }
    @PostMapping("/insertCountry")
    public String insertSortCountryData() {
        sanSortInsertWriteService.executeCountryInsertQuery();
        return "SortCountryData inserted successfully";
    }
    @PostMapping("/insertIdentification")
    public String insertSortIdentificationData() {
        sanSortInsertWriteService.executeIdentificatioInsertQuery();
        return "SortIdentificationData inserted successfully";
    }
    @PostMapping("/insertProgram")
    public String insertSortProgramData() {
        sanSortInsertWriteService.executeprogramInsertQuery();
        return "Insert query executed successfully.";
    }
    @PostMapping("/insertType")
    public String insertSortTypeData() {
        sanSortInsertWriteService.executeTypeInsertQuery();
        return "SortTypeData inserted successfully";
    }
}
