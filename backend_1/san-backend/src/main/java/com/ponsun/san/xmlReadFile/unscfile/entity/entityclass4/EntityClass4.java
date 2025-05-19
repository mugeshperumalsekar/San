package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass4;

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
@Table(name = "individual_4")
public class EntityClass4 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(mappedBy = "entityClass4")
    private EntityClass1 entityClass1;

    @Column(name = "dataid", length = 100)
    private String dataid;

    @Column(name = "individual_alias_4_city_of_birth", length = 100)
    private String individualAlias4CityOfBirth;

    @Column(name = "individual_alias_4_country_of_birth", length = 100)
    private String individualAlias4CountryOfBirth;

    @Column(name = "individual_alias_5_date_of_birth", length = 100)
    private String individualAlias5DateOfBirth;

    @Column(name = "individual_alias_5_city_of_birth", length = 100)
    private String individualAlias5CityOfBirth;

    @Column(name = "individual_alias_5_country_of_birth", length = 100)
    private String individualAlias5CountryOfBirth;

    @Column(name = "individual_alias_6_date_of_birth", length = 100)
    private String individualAlias6DateOfBirth;

    @Column(name = "individual_alias_6_city_of_birth", length = 100)
    private String individualAlias6CityOfBirth;

    @Column(name = "individual_alias_6_country_of_birth", length = 100)
    private String individualAlias6CountryOfBirth;

    @Column(name = "individual_alias_7_date_of_birth", length = 100)
    private String individualAlias7DateOfBirth;

    @Column(name = "individual_alias_7_city_of_birth", length = 100)
    private String individualAlias7CityOfBirth;

    @Column(name = "individual_alias_7_country_of_birth", length = 100)
    private String individualAlias7CountryOfBirth;

    @Column(name = "individual_alias_8_date_of_birth", length = 100)
    private String individualAlias8DateOfBirth;

    @Column(name = "individual_alias_8_city_of_birth", length = 100)
    private String individualAlias8CityOfBirth;

    @Column(name = "individual_alias_8_country_of_birth", length = 100)
    private String individualAlias8CountryOfBirth;

    @Column(name = "individual_alias_9_date_of_birth", length = 100)
    private String individualAlias9DateOfBirth;

    @Column(name = "individual_alias_10_date_of_birth", length = 100)
    private String individualAlias10DateOfBirth;

    @Column(name = "individual_alias_10_city_of_birth", length = 100)
    private String individualAlias10CityOfBirth;

    @Column(name = "individual_alias_10_country_of_birth", length = 100)
    private String individualAlias10CountryOfBirth;

    @Column(name = "individual_alias_11_date_of_birth", length = 100)
    private String individualAlias11DateOfBirth;

    @Column(name = "individual_alias_11_city_of_birth", length = 100)
    private String individualAlias11CityOfBirth;

    @Column(name = "individual_alias_11_country_of_birth", length = 100)
    private String individualAlias11CountryOfBirth;

    @Column(name = "individual_alias_12_date_of_birth", length = 100)
    private String individualAlias12DateOfBirth;

    @Column(name = "individual_alias_12_city_of_birth", length = 100)
    private String individualAlias12CityOfBirth;

    @Column(name = "individual_alias_12_country_of_birth", length = 100)
    private String individualAlias12CountryOfBirth;

    @Column(name = "individual_alias_13_date_of_birth", length = 100)
    private String individualAlias13DateOfBirth;

    @Column(name = "individual_alias_14_date_of_birth", length = 100)
    private String individualAlias14DateOfBirth;

    @Column(name = "individual_alias_14_country_of_birth", length = 100)
    private String individualAlias14CountryOfBirth;

    @Column(name = "individual_alias_15_date_of_birth", length = 100)
    private String individualAlias15DateOfBirth;

    @Column(name = "individual_alias_15_city_of_birth", length = 100)
    private String individualAlias15CityOfBirth;

    @Column(name = "individual_alias_15_country_of_birth", length = 100)
    private String individualAlias15CountryOfBirth;

    @Column(name = "individual_alias_5_note", length = 100)
    private String individualAlias5Note;

    @Column(name = "individual_address_1_city", columnDefinition = "TEXT")
    private String individualAddress1City;

    @Column(name = "individual_place_of_birth_0_note", length = 100)
    private String individualPlaceOfBirth0Note;

    @Column(name = "individual_alias_10_note", length = 100)
    private String individualAlias10Note;

    @Column(name = "individual_date_of_birth_2_date", length = 100)
    private String individualDateOfBirth2Date;

    @Column(name = "individual_address_2_city", columnDefinition = "TEXT")
    private String individualAddress2City;

    @Column(name = "individual_address_2_state_province", length = 100)
    private String individualAddress2StateProvince;

    @Column(name = "individual_alias_6_note", length = 100)
    private String individualAlias6Note;

    @Column(name = "individual_address_3_city", length = 100)
    private String individualAddress3City;

    @Column(name = "individual_address_3_state_province", length = 100)
    private String individualAddress3StateProvince;

    @Column(name = "individual_address_3_country", length = 100)
    private String individualAddress3Country;

    @Column(name = "individual_date_of_birth_1_from_year", length = 100)
    private String individualDateOfBirth1FromYear;

    @Column(name = "individual_date_of_birth_1_to_year", length = 100)
    private String individualDateOfBirth1ToYear;

    @Column(name = "individual_date_of_birth_2_note", length = 100)
    private String individualDateOfBirth2Note;

    @Column(name = "individual_date_of_birth_2_from_year", length = 100)
    private String individualDateOfBirth2FromYear;

    @Column(name = "individual_date_of_birth_2_to_year", length = 100)
    private String individualDateOfBirth2ToYear;

    @Column(name = "individual_address_2_note", length = 100)
    private String individualAddress2Note;

    @Column(name = "individual_date_of_birth_3_date", length = 100)
    private String individualDateOfBirth3Date;

    @Column(name = "individual_address_3_street", length = 100)
    private String individualAddress3Street;

    @Column(name = "individual_document_2_country_of_issue", length = 100)
    private String individualDocument2CountryOfIssue;

    @Column(name = "individual_alias_7_note", columnDefinition = "TEXT")
    private String individualAlias7Note;

    @Column(name = "individual_alias_8_note", length = 100)
    private String individualAlias8Note;

    @Column(name = "individual_alias_11_note", length = 100)
    private String individualAlias11Note;

    @Column(name = "individual_alias_12_note", length = 100)
    private String individualAlias12Note;

    @Column(name = "individual_alias_13_note", length = 100)
    private String individualAlias13Note;

    @Column(name = "individual_document_2_note", length = 100)
    private String individualDocument2Note;

    @Column(name = "individual_address_4_city", length = 100)
    private String individualAddress4City;

    @Column(name = "individual_address_4_country", length = 100)
    private String individualAddress4Country;

    @Column(name = "individual_address_5_city", length = 100)
    private String individualAddress5City;

    @Column(name = "individual_address_5_country", length = 100)
    private String individualAddress5Country;

    @Column(name = "individual_address_3_note", length = 100)
    private String individualAddress3Note;

    @Column(name = "individual_alias_9_note", length = 100)
    private String individualAlias9Note;

    @Column(name = "individual_address_6_city", length = 100)
    private String individualAddress6City;

    @Column(name = "individual_address_6_country", length = 100)
    private String individualAddress6Country;

    @Column(name = "individual_address_7_city", length = 100)
    private String individualAddress7City;

    @Column(name = "individual_address_7_note", columnDefinition = "TEXT")
    private String individualAddress7Note;

    @Column(name = "individual_date_of_birth_4_date", length = 100)
    private String individualDateOfBirth4Date;

    @Column(name = "individual_date_of_birth_6_note", length = 100)
    private String individualDateOfBirth6Note;

    @Column(name = "individual_date_of_birth_7_note", length = 100)
    private String individualDateOfBirth7Note;

    @Column(name = "individual_date_of_birth_8_date", length = 100)
    private String individualDateOfBirth8Date;

    @Column(name = "individual_date_of_birth_9_type_of_date", length = 100)
    private String individualDateOfBirth9TypeOfDate;

    @Column(name = "individual_date_of_birth_9_note", length = 100)
    private String individualDateOfBirth9Note;

    @Column(name = "individual_document_4_type_of_document2", length = 100)
    private String individualDocument4TypeOfDocument2;

    @Column(name = "individual_document_4_note", length = 100)
    private String individualDocument4Note;

    @Column(name = "nationality_value_2", length = 100)
    private String nationalityValue2;

    @Column(name = "last_day_updated_value_10", length = 100)
    private String lastDayUpdatedValue10;

    @Column(name = "last_day_updated_value_11", length = 100)
    private String lastDayUpdatedValue11;

    @Column(name = "individual_date_of_birth_1_note", length = 100)
    private String individualDateOfBirth1Note;

    public static EntityClass4 create(final Map<String, Object> row) {
        final EntityClass4 entityClass4 = new EntityClass4();

        entityClass4.setDataid(getString(row, "DATAID"));
        entityClass4.setIndividualAlias4CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/4/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias4CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/4/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias5DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/5/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias5CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/5/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias5CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/5/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias6DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/6/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias6CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/6/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias6CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/6/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias7DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/7/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias7CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/7/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias7CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/7/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias8DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/8/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias8CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/8/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias8CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/8/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias9DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/9/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias10DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/10/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias10CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/10/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias10CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/10/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias11DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/11/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias11CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/11/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias11CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/11/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias12DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/12/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias12CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/12/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias12CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/12/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias13DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/13/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias14DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/14/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias14CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/14/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias15DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/15/DATE_OF_BIRTH"));
        entityClass4.setIndividualAlias15CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/15/CITY_OF_BIRTH"));
        entityClass4.setIndividualAlias15CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/15/COUNTRY_OF_BIRTH"));
        entityClass4.setIndividualAlias5Note(getString(row, "INDIVIDUAL_ALIAS/5/NOTE"));
        entityClass4.setIndividualAddress1City(getString(row, "INDIVIDUAL_ADDRESS/1/CITY"));
        entityClass4.setIndividualPlaceOfBirth0Note(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH_0_NOTE"));
        entityClass4.setIndividualAlias10Note(getString(row, "INDIVIDUAL_ALIAS/10/NOTE"));
        entityClass4.setIndividualDateOfBirth2Date(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/2/DATE"));
        entityClass4.setIndividualAddress2City(getString(row, "INDIVIDUAL_ADDRESS/2/CITY"));
        entityClass4.setIndividualAddress2StateProvince(getString(row, "INDIVIDUAL_ADDRESS/2/STATE_PROVINCE"));
        entityClass4.setIndividualAlias6Note(getString(row, "INDIVIDUAL_ALIAS/6/NOTE"));
        entityClass4.setIndividualAddress3City(getString(row, "INDIVIDUAL_ADDRESS/3/CITY"));
        entityClass4.setIndividualAddress3StateProvince(getString(row, "INDIVIDUAL_ADDRESS/3/STATE_PROVINCE"));
        entityClass4.setIndividualAddress3Country(getString(row, "INDIVIDUAL_ADDRESS/3/COUNTRY"));
        entityClass4.setIndividualDateOfBirth1FromYear(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/1/FROM_YEAR"));
        entityClass4.setIndividualDateOfBirth1ToYear(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/1/TO_YEAR"));
        entityClass4.setIndividualDateOfBirth2Note(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/2/NOTE"));
        entityClass4.setIndividualDateOfBirth2FromYear(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/2/FROM_YEAR"));
        entityClass4.setIndividualDateOfBirth2ToYear(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/2/TO_YEAR"));
        entityClass4.setIndividualAddress2Note(getString(row, "INDIVIDUAL_ADDRESS/2/NOTE"));
        entityClass4.setIndividualDateOfBirth3Date(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/3/DATE"));
        entityClass4.setIndividualAddress3Street(getString(row, "INDIVIDUAL_ADDRESS/3/STREET"));
        entityClass4.setIndividualDocument2CountryOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/2/COUNTRY_OF_ISSUE"));
        entityClass4.setIndividualAlias7Note(getString(row, "INDIVIDUAL_ALIAS/7/NOTE"));
        entityClass4.setIndividualAlias8Note(getString(row, "INDIVIDUAL_ALIAS/8/NOTE"));
        entityClass4.setIndividualAlias11Note(getString(row, "INDIVIDUAL_ALIAS/11/NOTE"));
        entityClass4.setIndividualAlias12Note(getString(row, "INDIVIDUAL_ALIAS/12/NOTE"));
        entityClass4.setIndividualAlias13Note(getString(row, "INDIVIDUAL_ALIAS/13/NOTE"));
        entityClass4.setIndividualDocument2Note(getString(row, "INDIVIDUAL_DOCUMENT/2/NOTE"));
        entityClass4.setIndividualAddress4City(getString(row, "INDIVIDUAL_ADDRESS/4/CITY"));
        entityClass4.setIndividualAddress4Country(getString(row, "INDIVIDUAL_ADDRESS/4/COUNTRY"));
        entityClass4.setIndividualAddress5City(getString(row, "INDIVIDUAL_ADDRESS/5/CITY"));
        entityClass4.setIndividualAddress5Country(getString(row, "INDIVIDUAL_ADDRESS/5/COUNTRY"));
        entityClass4.setIndividualAddress3Note(getString(row, "INDIVIDUAL_ADDRESS/3/NOTE"));
        entityClass4.setIndividualAlias9Note(getString(row, "INDIVIDUAL_ALIAS/9/NOTE"));
        entityClass4.setIndividualAddress6City(getString(row, "INDIVIDUAL_ADDRESS/6/CITY"));
        entityClass4.setIndividualAddress6Country(getString(row, "INDIVIDUAL_ADDRESS/6/COUNTRY"));
        entityClass4.setIndividualAddress7City(getString(row, "INDIVIDUAL_ADDRESS/7/CITY"));
        entityClass4.setIndividualAddress7Note(getString(row, "INDIVIDUAL_ADDRESS/7/NOTE"));
        entityClass4.setIndividualDateOfBirth4Date(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/4/DATE"));
        entityClass4.setIndividualDateOfBirth6Note(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/6/NOTE"));
        entityClass4.setIndividualDateOfBirth7Note(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/7/NOTE"));
        entityClass4.setIndividualDateOfBirth8Date(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/8/DATE"));
        entityClass4.setIndividualDateOfBirth9TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/9/TYPE_OF_DATE"));
        entityClass4.setIndividualDateOfBirth9Note(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/9/NOTE"));
        entityClass4.setIndividualDocument4TypeOfDocument2(getString(row, "INDIVIDUAL_DOCUMENT/4/TYPE_OF_DOCUMENT2"));
        entityClass4.setIndividualDocument4Note(getString(row, "INDIVIDUAL_DOCUMENT/4/NOTE"));
        entityClass4.setNationalityValue2(getString(row, "NATIONALITY/VALUE/2"));
        entityClass4.setLastDayUpdatedValue10(getString(row, "LAST_DAY_UPDATED/VALUE/10"));
        entityClass4.setLastDayUpdatedValue11(getString(row, "LAST_DAY_UPDATED/VALUE/11"));
        entityClass4.setIndividualDateOfBirth1Note(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/1/NOTE"));
        entityClass4.setStatus(Status.ACTIVE);
        entityClass4.setCreatedAt(LocalDateTime.now());
        return entityClass4;
    }


    private static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        return (value != null) ? value.toString().trim() : "";
    }
}
