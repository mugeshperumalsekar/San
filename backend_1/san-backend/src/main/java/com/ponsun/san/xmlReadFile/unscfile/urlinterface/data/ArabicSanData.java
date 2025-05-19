package com.ponsun.san.xmlReadFile.unscfile.urlinterface.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

    public ArabicSanData(Integer personId, String name, String fullDate, String idValue, String countryName) {
        this.PersonId = personId;
        this.Name = name;
        this.FullDate = fullDate;
        this.IdValue = idValue;
        this.CountryName = countryName;
    }

    public static ArabicSanData newInstance(final Integer PersonId, final String NAME, final String FullDate, final String IdValue, final String CountryName) {
        return new ArabicSanData(PersonId, NAME, FullDate, IdValue, CountryName);
    }
}
