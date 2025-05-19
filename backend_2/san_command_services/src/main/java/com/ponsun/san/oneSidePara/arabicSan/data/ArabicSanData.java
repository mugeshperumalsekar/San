package com.ponsun.san.oneSidePara.arabicSan.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public ArabicSanData() {
        // Initialize any default values here if needed
    }
    // Normal Constructor
    public ArabicSanData(Integer personId, String name, String fullDate, String idValue,String countryName) {
        this.PersonId = personId;
        this.Name = name;
        this.FullDate = fullDate;
        this.IdValue = idValue;
        this.CountryName=countryName;

    }
    public static ArabicSanData newInstance(Integer personId, String name, String fullDate, String idValue,String countryName){
        return new ArabicSanData(personId,name,fullDate,idValue,countryName);
    }
}
