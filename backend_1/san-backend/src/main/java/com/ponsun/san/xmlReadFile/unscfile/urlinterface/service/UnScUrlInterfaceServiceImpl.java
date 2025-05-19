package com.ponsun.san.xmlReadFile.unscfile.urlinterface.service;

import com.ponsun.san.algorithm.ScoringCalculatorService;
import com.ponsun.san.searchLifcycle.search.services.SearchWritePlatformService;
import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.commonDto.service.AlgorithmSearchService;
import com.ponsun.san.xmlReadFile.unscfile.urlinterface.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Slf4j
public class UnScUrlInterfaceServiceImpl implements UnScUrlInterfaceService {

    private final SearchWritePlatformService searchWritePlatformService;
    private final ScoringCalculatorService scoringCalculatorService;
    private RestTemplate restTemplate = new RestTemplate();
    private final JdbcTemplate jdbcTemplate;
    private final AlgorithmSearchService algorithmSearchService;

    @Override
    public List<UiSingleParaResponse> searchSanctionedPersons(MultiParaSearchData multiParaSearchData, Integer fileType) throws ExecutionException, InterruptedException {
        List<IndividualInfoDto> arabicDataList = getIndividualInfo();

        List<ArabicSanData> filtered = arabicDataList.stream()
                .map(d -> {
                    String countryName = Stream.of(
                                    d.getAddressCountry(),
                                    d.getBirthCountry(),
                                    d.getNationality(),
                                    d.getBirthPlaceCountry()
                            )
                            .filter(s -> s != null && !s.isEmpty())
                            .collect(Collectors.joining(","));

                    return new ArabicSanData(
                            d.getPersonId(),
                            d.getName().trim().toLowerCase(),
                            d.getBirthDate(),
                            d.getIdValues(),
                            countryName.trim().toLowerCase()
                    );
                })
                .toList();
//        return calculateAlgorithmSearch(multiParaSearchData,filtered);

        try {
            return algorithmSearchService.calculateAlgorithmSearch(multiParaSearchData, filtered,fileType);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to perform algorithm search", e);
        }
    }

    @Override
    public List<IndividualInfoDto> getIndividualInfo() {
        String sql = """
            SELECT
                CONCAT_WS(' ', e.first_name, e.second_name, e.third_name, e.fourth_name) AS name,
                e.data_id AS personId,
                GROUP_CONCAT(DISTINCT a.country) AS addressCountry,
                GROUP_CONCAT(DISTINCT b.country_of_birth) AS birthCountry,
                GROUP_CONCAT(DISTINCT d.nationality) AS nationality,
                GROUP_CONCAT(DISTINCT g.country) AS birthPlaceCountry,
                GROUP_CONCAT(DISTINCT c.date) AS birthDate,
                GROUP_CONCAT(DISTINCT f.number) AS idValues
            FROM
                unsc_individuals e
            LEFT JOIN unsc_addresses a ON e.id = a.individual_id
            LEFT JOIN unsc_aliases b ON e.id = b.individual_id
            LEFT JOIN unsc_dates_of_birth c ON e.id = c.individual_id
            LEFT JOIN unsc_nationalities d ON e.id = d.individual_id
            LEFT JOIN unsc_documents f ON e.id = f.individual_id
            LEFT JOIN unsc_places_of_birth g ON e.id = g.individual_id
            GROUP BY e.id
        """;

        RowMapper<IndividualInfoDto> mapper = (rs, rowNum) -> new IndividualInfoDto(
                rs.getString("name"),
                rs.getInt("personId"),
                rs.getString("addressCountry"),
                rs.getString("birthCountry"),
                rs.getString("nationality"),
                rs.getString("birthPlaceCountry"),
                rs.getString("birthDate"),
                rs.getString("idValues")
        );

        return jdbcTemplate.query(sql, mapper);
    }
}

//
//LIC - 2617
//PO - 500
//BUS - 500
//RD - 1000
//IOB - 994
//DRESS - 2000