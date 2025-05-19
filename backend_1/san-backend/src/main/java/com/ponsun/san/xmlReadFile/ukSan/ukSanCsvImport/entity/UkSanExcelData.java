package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.dto.UkSanDataDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "location_excel_data")
public class UkSanExcelData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "relatedLocation")
    private String relatedLocation;

    @Column(name = "sortCode")
    private String sortCode;

    @Column(name = "firstAddress")
    private String firstAddress;

    @Column(name = "secondAddress")
    private String secondAddress;

    @Column(name = "thirdAddress")
    private String thirdAddress;

    @Column(name = "locationName")
    private String locationName;

    @Column(name = "area")
    private String area;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "locationDescription")
    private String locationDescription;

    @Column(name = "region")
    private String region;

    @Column(name = "BICCode")
    private String BICCode;


    public static UkSanExcelData create(final UkSanDataDto ukSanDataDto){
        final UkSanExcelData ukSanExcelData = new UkSanExcelData();
        ukSanExcelData.setRelatedLocation(ukSanDataDto.getRelatedLocation());
        ukSanExcelData.setSortCode(ukSanDataDto.getSortCode());
        ukSanExcelData.setFirstAddress(ukSanDataDto.getFirstAddress());
        ukSanExcelData.setSecondAddress(ukSanDataDto.getSecondAddress());
        ukSanExcelData.setThirdAddress(ukSanDataDto.getThirdAddress());
        ukSanExcelData.setLocationName(ukSanDataDto.getLocationName());
        ukSanExcelData.setArea(ukSanDataDto.getArea());
        ukSanExcelData.setPincode(ukSanDataDto.getPincode());
        ukSanExcelData.setCountry(ukSanDataDto.getCountry());
        ukSanExcelData.setState(ukSanDataDto.getState());
        ukSanExcelData.setCity(ukSanDataDto.getCity());
        ukSanExcelData.setLocationDescription(ukSanDataDto.getLocationDescription());
        ukSanExcelData.setRegion(ukSanDataDto.getRegion());
        ukSanExcelData.setBICCode(ukSanDataDto.getBICCode());
        ukSanExcelData.setStatus(Status.ACTIVE);
        ukSanExcelData.setCreatedAt(LocalDateTime.now());
        return ukSanExcelData;
    }
}
