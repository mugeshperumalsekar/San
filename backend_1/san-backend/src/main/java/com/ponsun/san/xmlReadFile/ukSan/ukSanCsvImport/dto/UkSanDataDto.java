package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.dto;

import lombok.Data;

@Data
public class UkSanDataDto {

    private String relatedLocation;
    private String sortCode;
    private String firstAddress;
    private String secondAddress;
    private String thirdAddress;
    private String locationName;
    private String area;
    private String pincode;
    private String country;
    private String state;
    private String city;
    private String locationDescription;
    private String region;
    private String BICCode;


    public UkSanDataDto(final String relatedLocation, final String sortCode, final String firstAddress, final String secondAddress, final String thirdAddress, final String locationName, final String area, final String pincode, final String country, final String state, final String city, final String locationDescription, final String region, final String BICCode) {

        this.relatedLocation = relatedLocation;
        this.sortCode = sortCode;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.thirdAddress = thirdAddress;
        this.locationName = locationName;
        this.area = area;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.locationDescription = locationDescription;
        this.region = region;
        this.BICCode = BICCode;

    }

    public static UkSanDataDto newInstance(final String relatedLocation, final String sortCode, final String firstAddress, final String secondAddress, final String thirdAddress, final String locationName, final String area, final String pincode, final String country, final String state, final String city, final String locationDescription, final String region, final String BICCode) {
        return new UkSanDataDto(
                relatedLocation,
                sortCode,
                firstAddress,
                secondAddress,
                thirdAddress,
                locationName,
                area,
                pincode,
                country,
                state,
                city,
                locationDescription,
                region,
                BICCode);
    }
}
