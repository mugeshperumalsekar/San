package com.ponsun.san.xmlReadFile.ukSan.urlInterFace.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.dto.UkSanDataDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class ArabicSanData {
    @JsonProperty("PersonId")
    private Integer PersonId;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("FullDate")
    private String FullDate;
    @JsonProperty("IdValue")
    private String IdValue;
    @JsonProperty("CountryName")
    private String CountryName;

    public ArabicSanData(Integer personId, String name, String fullDate, String idValue, String countryName) {
        this.PersonId = personId;
        this.Name = name;
        this.FullDate = fullDate;
        this.IdValue = idValue;
        this.CountryName = countryName;
    }

    public static ArabicSanData newInstance(final Integer PersonId, final String NAME, final String FullDate, final String IdValue, final String CountryName){
        return new ArabicSanData(PersonId, NAME, FullDate, IdValue, CountryName);
    }

}
