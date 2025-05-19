package com.ponsun.san.xmlReadFile.euSan.personCountry.api;

import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.euSan.personCountry.service.PersonCountryReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/personCountryApiResources")
@Tag(name = "personCountryApiResources")
public class personCountryApiResources {

    private final PersonCountryReadService readService;


    @GetMapping("/personDetails")
    public List<ArabicSanData> getAllPersonDetails() {
        return readService.getAllPersonDetails();
    }
}
