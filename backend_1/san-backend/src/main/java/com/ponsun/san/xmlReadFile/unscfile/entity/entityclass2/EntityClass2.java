package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass2;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1.EntityClass1;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "individual_2")
public class EntityClass2 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 100)
    private Integer id;

    @OneToOne(mappedBy = "entityClass2")
    private EntityClass1 entityClass1;

    @Column(name = "dataid", length = 100)
    private String dataid;

    @Column(name = "individual_alias_10_alias_name", length = 100)
    private String individualAlias10AliasName;

    @Column(name = "individual_alias_11_quality", length = 100)
    private String individualAlias11Quality;

    @Column(name = "individual_alias_11_alias_name", length = 100)
    private String individualAlias11AliasName;

    @Column(name = "individual_alias_12_quality", length = 100)
    private String individualAlias12Quality;

    @Column(name = "individual_alias_12_alias_name", length = 100)
    private String individualAlias12AliasName;

    @Column(name = "individual_alias_13_quality", length = 100)
    private String individualAlias13Quality;

    @Column(name = "individual_alias_13_alias_name", length = 100)
    private String individualAlias13AliasName;

    @Column(name = "individual_alias_14_quality", length = 100)
    private String individualAlias14Quality;

    @Column(name = "individual_alias_14_alias_name", length = 100)
    private String individualAlias14AliasName;

    @Column(name = "individual_alias_15_quality", length = 100)
    private String individualAlias15Quality;

    @Column(name = "individual_alias_15_alias_name", length = 100)
    private String individualAlias15AliasName;

    @Column(name = "individual_place_of_birth_2_city", length = 100)
    private String individualPlaceOfBirth2City;

    @Column(name = "individual_place_of_birth_2_state_province", length = 100)
    private String individualPlaceOfBirth2StateProvince;

    @Column(name = "individual_place_of_birth_2_country", length = 100)
    private String individualPlaceOfBirth2Country;

    @Column(name = "individual_place_of_birth_3_city", length = 100)
    private String individualPlaceOfBirth3City;

    @Column(name = "individual_place_of_birth_3_state_province", length = 100)
    private String individualPlaceOfBirth3StateProvince;

    @Column(name = "individual_place_of_birth_3_country", length = 100)
    private String individualPlaceOfBirth3Country;

    @Column(name = "individual_place_of_birth_4_city", length = 100)
    private String individualPlaceOfBirth4City;

    @Column(name = "individual_place_of_birth_4_state_province", length = 100)
    private String individualPlaceOfBirth4StateProvince;

    @Column(name = "individual_place_of_birth_4_country", length = 100)
    private String individualPlaceOfBirth4Country;

    @Column(name = "individual_date_of_birth_0_from_year", length = 100)
    private String individualDateOfBirth0FromYear;

    @Column(name = "individual_date_of_birth_0_to_year", length = 100)
    private String individualDateOfBirth0ToYear;

    @Column(name = "individual_alias_0_note", length = 100)
    private String individualAlias0Note;

    @Column(name = "individual_date_of_birth_0_note", length = 100)
    private String individualDateOfBirth0Note;

    @Column(name = "last_day_updated_value_7", length = 100)
    private String lastDayUpdatedValue7;

    @Column(name = "last_day_updated_value_8", length = 100)
    private String lastDayUpdatedValue8;

    @Column(name = "last_day_updated_value_9", length = 100)
    private String lastDayUpdatedValue9;

    @Column(name = "individual_alias_1_date_of_birth", length = 100)
    private String individualAlias1DateOfBirth;

    @Column(name = "individual_document_0_country_of_issue", length = 100)
    private String individualDocument0CountryOfIssue;

    @Column(name = "individual_date_of_birth_1_date", length = 100)
    private String individualDateOfBirth1Date;

    @Column(name = "individual_alias_1_note", columnDefinition = "TEXT")
    private String individualAlias1Note;

    @Column(name = "nationality_value_1", length = 100)
    private String nationalityValue1;

    @Column(name = "fourth_name", length = 100)
    private String fourthName;

    @Column(name = "title_value_2", length = 100)
    private String titleValue2;

    @Column(name = "individual_place_of_birth_2_street", length = 100)
    private String individualPlaceOfBirth2Street;

    @Column(name = "designation_value_3", length = 100)
    private String designationValue3;

    @Column(name = "individual_place_of_birth_0_street", length = 100)
    private String individualPlaceOfBirth0Street;

    @Column(name = "individual_document_0_note", length = 100)
    private String individualDocument0Note;

    @Column(name = "individual_document_0_issuing_country", length = 100)
    private String individualDocument0IssuingCountry;

    @Column(name = "individual_document_0_date_of_issue", length = 100)
    private String individualDocument0DateOfIssue;

    @Column(name = "individual_document_0_city_of_issue", length = 100)
    private String individualDocument0CityOfIssue;

    @Column(name = "individual_alias_2_city_of_birth", length = 100)
    private String individualAlias2CityOfBirth;

    @Column(name = "individual_alias_2_country_of_birth", length = 100)
    private String individualAlias2CountryOfBirth;

    @Column(name = "individual_alias_2_note", columnDefinition = "TEXT")
    private String individualAlias2Note;

    @Column(name = "individual_alias_3_note",  columnDefinition = "TEXT")
    private String individualAlias3Note;

    @Column(name = "individual_document_1_issuing_country", length = 100)
    private String individualDocument1IssuingCountry;

    @Column(name = "individual_document_1_date_of_issue", length = 100)
    private String individualDocument1DateOfIssue;

    @Column(name = "individual_document_1_note", columnDefinition = "TEXT")
    private String individualDocument1Note;

    @Column(name = "individual_address_0_street", length = 100)
    private String individualAddress0Street;

    @Column(name = "individual_address_0_city", length = 100)
    private String individualAddress0City;

    @Column(name = "individual_date_of_birth_0_date", length = 100)
    private String individualDateOfBirth0Date;

    @Column(name = "individual_address_0_state_province", length = 100)
    private String individualAddress0StateProvince;

    @Column(name = "individual_address_0_note",columnDefinition = "TEXT")
    private String individualAddress0Note;

    @Column(name = "individual_alias_0_date_of_birth", length = 100)
    private String individualAlias0DateOfBirth;

    @Column(name = "individual_alias_0_city_of_birth", length = 100)
    private String individualAlias0CityOfBirth;

    @Column(name = "individual_alias_0_country_of_birth", length = 100)
    private String individualAlias0CountryOfBirth;

    @Column(name = "individual_alias_1_city_of_birth", length = 100)
    private String individualAlias1CityOfBirth;

    @Column(name = "individual_alias_1_country_of_birth", length = 100)
    private String individualAlias1CountryOfBirth;

    @Column(name = "individual_address_0_zip_code", length = 100)
    private String individualAddress0ZipCode;

    @Column(name = "individual_document_1_city_of_issue", length = 100)
    private String individualDocument1CityOfIssue;

    @Column(name = "individual_document_1_country_of_issue", length = 100)
    private String individualDocument1CountryOfIssue;

    @Column(name = "individual_document_2_type_of_document", length = 100)
    private String individualDocument2TypeOfDocument;

    @Column(name = "individual_document_2_type_of_document2", length = 100)
    private String individualDocument2TypeOfDocument2;

    @Column(name = "individual_document_2_number", length = 100)
    private String individualDocument2Number;

    @Column(name = "individual_document_2_issuing_country", length = 100)
    private String individualDocument2IssuingCountry;

    @Column(name = "individual_date_of_birth_3_type_of_date", length = 100)
    private String individualDateOfBirth3TypeOfDate;

    @Column(name = "individual_date_of_birth_3_year", length = 100)
    private String individualDateOfBirth3Year;

    @Column(name = "individual_date_of_birth_4_type_of_date", length = 100)
    private String individualDateOfBirth4TypeOfDate;

    @Column(name = "individual_date_of_birth_4_year", length = 100)
    private String individualDateOfBirth4Year;

    @Column(name = "individual_date_of_birth_5_type_of_date", length = 100)
    private String individualDateOfBirth5TypeOfDate;

    @Column(name = "individual_date_of_birth_5_year", length = 100)
    private String individualDateOfBirth5Year;

    @Column(name = "individual_date_of_birth_6_type_of_date", length = 100)
    private String individualDateOfBirth6TypeOfDate;

    @Column(name = "individual_date_of_birth_6_year", length = 100)
    private String individualDateOfBirth6Year;

    @Column(name = "individual_date_of_birth_7_type_of_date", length = 100)
    private String individualDateOfBirth7TypeOfDate;

    @Column(name = "individual_date_of_birth_7_year", length = 100)
    private String individualDateOfBirth7Year;

    public static EntityClass2 create(final Map<String, Object> row){
        final EntityClass2 entityClass2 = new EntityClass2();
        entityClass2.setDataid(getString(row, "DATAID"));
        entityClass2.setIndividualAlias10AliasName(getString(row, "INDIVIDUAL_ALIAS/10/ALIAS_NAME"));
        entityClass2.setIndividualAlias11Quality(getString(row, "INDIVIDUAL_ALIAS/11/QUALITY"));
        entityClass2.setIndividualAlias11AliasName(getString(row, "INDIVIDUAL_ALIAS/11/ALIAS_NAME"));
        entityClass2.setIndividualAlias12Quality(getString(row, "INDIVIDUAL_ALIAS/12/QUALITY"));
        entityClass2.setIndividualAlias12AliasName(getString(row, "INDIVIDUAL_ALIAS/12/ALIAS_NAME"));
        entityClass2.setIndividualAlias13Quality(getString(row, "INDIVIDUAL_ALIAS/13/QUALITY"));
        entityClass2.setIndividualAlias13AliasName(getString(row, "INDIVIDUAL_ALIAS/13/ALIAS_NAME"));
        entityClass2.setIndividualAlias14Quality(getString(row, "INDIVIDUAL_ALIAS/14/QUALITY"));
        entityClass2.setIndividualAlias14AliasName(getString(row, "INDIVIDUAL_ALIAS/14/ALIAS_NAME"));
        entityClass2.setIndividualAlias15Quality(getString(row, "INDIVIDUAL_ALIAS/15/QUALITY"));
        entityClass2.setIndividualAlias15AliasName(getString(row, "INDIVIDUAL_ALIAS/15/ALIAS_NAME"));
        entityClass2.setIndividualPlaceOfBirth2City(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/2/CITY"));
        entityClass2.setIndividualPlaceOfBirth2StateProvince(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/2/STATE_PROVINCE"));
        entityClass2.setIndividualPlaceOfBirth2Country(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/2/COUNTRY"));
        entityClass2.setIndividualPlaceOfBirth3City(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/3/CITY"));
        entityClass2.setIndividualPlaceOfBirth3StateProvince(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/3/STATE_PROVINCE"));
        entityClass2.setIndividualPlaceOfBirth3Country(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/3/COUNTRY"));
        entityClass2.setIndividualPlaceOfBirth4City(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/4/CITY"));
        entityClass2.setIndividualPlaceOfBirth4StateProvince(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/4/STATE_PROVINCE"));
        entityClass2.setIndividualPlaceOfBirth4Country(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/4/COUNTRY"));
        entityClass2.setIndividualDateOfBirth0FromYear(getString(row, "INDIVIDUAL_DATE_OF_BIRTH_0_FROM_YEAR"));
        entityClass2.setIndividualDateOfBirth0ToYear(getString(row, "INDIVIDUAL_DATE_OF_BIRTH_0_TO_YEAR"));
        entityClass2.setIndividualAlias0Note(getString(row, "INDIVIDUAL_ALIAS_0_NOTE"));
        entityClass2.setIndividualDateOfBirth0Note(getString(row, "INDIVIDUAL_DATE_OF_BIRTH_0_NOTE"));
        entityClass2.setLastDayUpdatedValue7(getString(row, "LAST_DAY_UPDATED/VALUE/7"));
        entityClass2.setLastDayUpdatedValue8(getString(row, "LAST_DAY_UPDATED/VALUE/8"));
        entityClass2.setLastDayUpdatedValue9(getString(row, "LAST_DAY_UPDATED/VALUE/9"));
        entityClass2.setIndividualAlias1DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/1/DATE_OF_BIRTH"));
        entityClass2.setIndividualDocument0CountryOfIssue(getString(row, "INDIVIDUAL_DOCUMENT_0_COUNTRY_OF_ISSUE"));
        entityClass2.setIndividualDateOfBirth1Date(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/1/DATE"));
        entityClass2.setIndividualAlias1Note(getString(row, "INDIVIDUAL_ALIAS/1/NOTE"));
        entityClass2.setNationalityValue1(getString(row, "NATIONALITY/VALUE/1"));
        entityClass2.setFourthName(getString(row, "FOURTH_NAME"));
        entityClass2.setTitleValue2(getString(row, "TITLE/VALUE/2"));
        entityClass2.setIndividualPlaceOfBirth2Street(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/2/STREET"));
        entityClass2.setDesignationValue3(getString(row, "DESIGNATION/VALUE/3"));
        entityClass2.setIndividualPlaceOfBirth0Street(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH_0_STREET"));
        entityClass2.setIndividualDocument0Note(getString(row, "INDIVIDUAL_DOCUMENT_0_NOTE"));
        entityClass2.setIndividualDocument0IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT_0_ISSUING_COUNTRY"));
        entityClass2.setIndividualDocument0DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT_0_DATE_OF_ISSUE"));
        entityClass2.setIndividualDocument0CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT_0_CITY_OF_ISSUE"));
        entityClass2.setIndividualAlias2CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/2/CITY_OF_BIRTH"));
        entityClass2.setIndividualAlias2CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/2/COUNTRY_OF_BIRTH"));
        entityClass2.setIndividualAlias2Note(getString(row, "INDIVIDUAL_ALIAS/2/NOTE"));
        entityClass2.setIndividualAlias3Note(getString(row, "INDIVIDUAL_ALIAS/3/NOTE"));
        entityClass2.setIndividualDocument1IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/1/ISSUING_COUNTRY"));
        entityClass2.setIndividualDocument1DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/1/DATE_OF_ISSUE"));
        entityClass2.setIndividualDocument1Note(getString(row, "INDIVIDUAL_DOCUMENT/1/NOTE"));
        entityClass2.setIndividualAddress0Street(getString(row, "INDIVIDUAL_ADDRESS_0_STREET"));
        entityClass2.setIndividualAddress0City(getString(row, "INDIVIDUAL_ADDRESS_0_CITY"));
        entityClass2.setIndividualDateOfBirth0Date(getString(row, "INDIVIDUAL_DATE_OF_BIRTH_0_DATE"));
        entityClass2.setIndividualAddress0StateProvince(getString(row, "INDIVIDUAL_ADDRESS_0_STATE_PROVINCE"));
        entityClass2.setIndividualAddress0Note(getString(row, "INDIVIDUAL_ADDRESS_0_NOTE"));
        entityClass2.setIndividualAlias0DateOfBirth(getString(row, "INDIVIDUAL_ALIAS_0_DATE_OF_BIRTH"));
        entityClass2.setIndividualAlias0CityOfBirth(getString(row, "INDIVIDUAL_ALIAS_0_CITY_OF_BIRTH"));
        entityClass2.setIndividualAlias0CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS_0_COUNTRY_OF_BIRTH"));
        entityClass2.setIndividualAlias1CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/1/CITY_OF_BIRTH"));
        entityClass2.setIndividualAlias1CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/1/COUNTRY_OF_BIRTH"));
        entityClass2.setIndividualAddress0ZipCode(getString(row, "INDIVIDUAL_ADDRESS_0_ZIP_CODE"));
        entityClass2.setIndividualDocument1CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/1/CITY_OF_ISSUE"));
        entityClass2.setIndividualDocument1CountryOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/1/COUNTRY_OF_ISSUE"));
        entityClass2.setIndividualDocument2TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/2/TYPE_OF_DOCUMENT"));
        entityClass2.setIndividualDocument2TypeOfDocument2(getString(row, "INDIVIDUAL_DOCUMENT/2/TYPE_OF_DOCUMENT2"));
        entityClass2.setIndividualDocument2Number(getString(row, "INDIVIDUAL_DOCUMENT/2/NUMBER"));
        entityClass2.setIndividualDocument2IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/2/ISSUING_COUNTRY"));
        entityClass2.setIndividualDateOfBirth3TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/3/TYPE_OF_DATE"));
        entityClass2.setIndividualDateOfBirth3Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/3/YEAR"));
        entityClass2.setIndividualDateOfBirth4TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/4/TYPE_OF_DATE"));
        entityClass2.setIndividualDateOfBirth4Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/4/YEAR"));
        entityClass2.setIndividualDateOfBirth5TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/5/TYPE_OF_DATE"));
        entityClass2.setIndividualDateOfBirth5Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/5/YEAR"));
        entityClass2.setIndividualDateOfBirth6TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/6/TYPE_OF_DATE"));
        entityClass2.setIndividualDateOfBirth6Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/6/YEAR"));
        entityClass2.setIndividualDateOfBirth7TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/7/TYPE_OF_DATE"));
        entityClass2.setIndividualDateOfBirth7Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/7/YEAR"));
        entityClass2.setStatus(Status.ACTIVE);
        entityClass2.setCreatedAt(LocalDateTime.now());
        return entityClass2;
    }


    private static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        return (value != null) ? value.toString().trim() : "";
    }

}
