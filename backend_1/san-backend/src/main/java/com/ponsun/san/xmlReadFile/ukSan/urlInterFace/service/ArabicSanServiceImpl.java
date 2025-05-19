package com.ponsun.san.xmlReadFile.ukSan.urlInterFace.service;

import com.ponsun.san.xmlReadFile.commonDto.Data.ArabicSanData;
import com.ponsun.san.xmlReadFile.commonDto.Data.MultiParaSearchData;
import com.ponsun.san.xmlReadFile.commonDto.Data.UiSingleParaResponse;
import com.ponsun.san.xmlReadFile.commonDto.service.AlgorithmSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArabicSanServiceImpl implements ArabicSanService {

    @Autowired
    private RestTemplate restTemplate;

    private final JdbcTemplate jdbcTemplate;

    private final AlgorithmSearchService algorithmSearchService;


    @Override
    public List<UiSingleParaResponse> searchSanctionedPersons(MultiParaSearchData multiParaSearchData,Integer fileType) {

        List<ArabicSanData> arabicDataList = getArabicSanctions();

        List<ArabicSanData> filtered = arabicDataList.stream()
                .map(d -> new ArabicSanData(
                        d.getPersonId(),
                        d.getName().trim().toLowerCase(),
                        d.getFullDate(),
                        d.getIdValue(),
                        d.getCountryName().trim().toLowerCase()
                ))
                .toList();

//        return this.algorithmSearchService.calculateAlgorithmSearch(multiParaSearchData, filtered,fileType);

        try {
            return algorithmSearchService.calculateAlgorithmSearch(multiParaSearchData, filtered,fileType);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to perform algorithm search", e);
        }
    }

    @Override
    public List<ArabicSanData> getArabicSanctions() {
        String sql = """
                SELECT
                   CONCAT_WS(' ',NULLIF(name_1, ''),NULLIF(name_2, ''),NULLIF(name_3, ''),NULLIF(name_4, ''),NULLIF(name_5, ''),NULLIF(name_6, '')) AS NAME,
                   CONCAT_WS(', ',
                     NULLIF(national_identifier_number, ''),
                     NULLIF(passport_number, ''),
                     NULLIF(business_registration_number__s_, ''),
                     NULLIF(hull_identification_number__hin_, '')
                   ) AS IdValue,
                   CONCAT_WS(', ',
                     NULLIF(address_country, ''),
                     NULLIF(nationality__ies_, ''),
                     NULLIF(country_of_birth, '')
                   ) AS CountryName,
                   d_o_b AS FullDate,
                   unique_id AS PersonId FROM sanctions_data_uk
                """;

        RowMapper<ArabicSanData> mapper = (rs, rowNum) -> {
            String rawPersonId = rs.getString("PersonId");
            Integer numericPersonId = extractNumber(rawPersonId);

            return new ArabicSanData(
                    numericPersonId,
                    rs.getString("NAME"),
                    rs.getString("FullDate"),
                    rs.getString("IdValue"),
                    rs.getString("CountryName")
            );
        };
        List<ArabicSanData> result = jdbcTemplate.query(sql, mapper);
        return result;
    }
    private Integer extractNumber(String input) {
        if (input == null) return null;
        String digits = input.replaceAll("\\D+", ""); // remove all non-digits
        return digits.isEmpty() ? null : Integer.parseInt(digits);
    }

}
