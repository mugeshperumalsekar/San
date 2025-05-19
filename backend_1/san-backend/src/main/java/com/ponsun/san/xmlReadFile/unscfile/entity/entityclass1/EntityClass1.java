package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass1;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.xmlReadFile.unscfile.entity.dateupdate.DateUpdate;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass2.EntityClass2;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass3.EntityClass3;
import com.ponsun.san.xmlReadFile.unscfile.entity.entityclass4.EntityClass4;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "individual_1")
public class EntityClass1 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entity_class_2_id")
    private EntityClass2 entityClass2;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entity_class_3_id", insertable = true, updatable = true)
    private EntityClass3 entityClass3;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entity_class_4_id", insertable = true, updatable = true)
    private EntityClass4 entityClass4;

    @OneToMany(mappedBy = "entityClass1", cascade = CascadeType.ALL)
    private List<DateUpdate> dateUpdated;

    @Column(name = "dataid", length = 100)
    private String dataid;

    @Column(name = "ver_b670sionnum", length = 100)
    private String verB670sionnum;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "second_name", length = 100)
    private String secondName;

    @Column(name = "third_name", length = 100)
    private String thirdName;

    @Column(name = "un_list_type", length = 100)
    private String unListType;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "listed_on", length = 100)
    private String listedOn;

    @Column(name = "name_original_script", columnDefinition = "TEXT")
    private String nameOriginalScript;

    @Column(name = "comments1", columnDefinition = "TEXT")
    private String comments1;

    @Column(name = "title_value_0", columnDefinition = "TEXT")
    private String titleValue0;

    @Column(name = "designation_value_0",columnDefinition = "TEXT")
    private String designationValue0;

    @Column(name = "designation_value_1", columnDefinition = "TEXT")
    private String designationValue1;

    @Column(name = "nationality_value_0", length = 100)
    private String nationalityValue0;

    @Column(name = "list_type_value_0", length = 100)
    private String listTypeValue0;

    @Column(name = "last_day_updated_value_0", length = 100)
    private String lastDayUpdatedValue0;

    @Column(name = "last_day_updated_value_1", length = 100)
    private String lastDayUpdatedValue1;

    @Column(name = "last_day_updated_value_2", length = 100)
    private String lastDayUpdatedValue2;

    @Column(name = "last_day_updated_value_3", length = 100)
    private String lastDayUpdatedValue3;

    @Column(name = "individual_alias_0_quality", length = 100)
    private String individualAlias0Quality;

    @Column(name = "individual_alias_0_alias_name", columnDefinition = "TEXT")
    private String individualAlias0AliasName;

    @Column(name = "individual_alias_1_quality", length = 100)
    private String individualAlias1Quality;

    @Column(name = "individual_alias_1_alias_name", length = 100)
    private String individualAlias1AliasName;

    @Column(name = "individual_alias_2_quality", length = 100)
    private String individualAlias2Quality;

    @Column(name = "individual_alias_2_alias_name", length = 100)
    private String individualAlias2AliasName;

    @Column(name = "individual_alias_3_quality", length = 100)
    private String individualAlias3Quality;

    @Column(name = "individual_alias_3_alias_name", length = 100)
    private String individualAlias3AliasName;

    @Column(name = "individual_address_0_country", length = 100)
    private String individualAddress0Country;

    @Column(name = "individual_date_of_birth_0_type_of_date", length = 100)
    private String individualDateOfBirth0TypeOfDate;

    @Column(name = "individual_date_of_birth_0_year", length = 100)
    private String individualDateOfBirth0Year;

    @Column(name = "individual_place_of_birth_0_city", length = 100)
    private String individualPlaceOfBirth0City;

    @Column(name = "individual_place_of_birth_0_state_province", length = 100)
    private String individualPlaceOfBirth0StateProvince;

    @Column(name = "individual_place_of_birth_0_country", length = 100)
    private String individualPlaceOfBirth0Country;

    @Column(name = "individual_document_0", length = 100)
    private String individualDocument0;

    @Column(name = "sort_key", length = 100)
    private String sortKey;

    @Column(name = "sort_key_last_mod", length = 100)
    private String sortKeyLastMod;

    @Column(name = "last_day_updated_value_4", length = 100)
    private String lastDayUpdatedValue4;

    @Column(name = "last_day_updated_value_5", length = 100)
    private String lastDayUpdatedValue5;

    @Column(name = "individual_place_of_birth_1_city", length = 100)
    private String individualPlaceOfBirth1City;

    @Column(name = "individual_place_of_birth_1_state_province", length = 100)
    private String individualPlaceOfBirth1StateProvince;

    @Column(name = "individual_place_of_birth_1_country", length = 100)
    private String individualPlaceOfBirth1Country;

    @Column(name = "designation_value_2", columnDefinition = "TEXT")
    private String designationValue2;

    @Column(name = "last_day_updated_value_6", length = 100)
    private String lastDayUpdatedValue6;

    @Column(name = "individual_date_of_birth_1_type_of_date", length = 100)
    private String individualDateOfBirth1TypeOfDate;

    @Column(name = "individual_date_of_birth_1_year", length = 100)
    private String individualDateOfBirth1Year;

    @Column(name = "individual_date_of_birth_2_type_of_date", length = 100)
    private String individualDateOfBirth2TypeOfDate;

    @Column(name = "individual_date_of_birth_2_year", length = 100)
    private String individualDateOfBirth2Year;

    @Column(name = "individual_place_of_birth_1_street", length = 100)
    private String individualPlaceOfBirth1Street;

    @Column(name = "title_value_1", length = 100)
    private String titleValue1;

    @Column(name = "individual_document_0_type_of_document", length = 100)
    private String individualDocument0TypeOfDocument;

    @Column(name = "individual_document_0_type_of_document2", length = 100)
    private String individualDocument0TypeOfDocument2;

    @Column(name = "individual_document_0_number", length = 100)
    private String individualDocument0Number;

    @Column(name = "individual_document_1_type_of_document", length = 100)
    private String individualDocument1TypeOfDocument;

    @Column(name = "individual_document_1_type_of_document2", length = 100)
    private String individualDocument1TypeOfDocument2;

    @Column(name = "individual_document_1_number", length = 100)
    private String individualDocument1Number;

    @Column(name = "gender", length = 100)
    private String gender;

    @Column(name = "individual_alias_4_quality", length = 100)
    private String individualAlias4Quality;

    @Column(name = "individual_alias_4_alias_name", length = 100)
    private String individualAlias4AliasName;

    @Column(name = "individual_alias_4_note", length = 100)
    private String individualAlias4Note;

    @Column(name = "individual_alias_5_quality", length = 100)
    private String individualAlias5Quality;

    @Column(name = "individual_alias_5_alias_name", length = 100)
    private String individualAlias5AliasName;

    @Column(name = "individual_alias_6_quality", length = 100)
    private String individualAlias6Quality;

    @Column(name = "individual_alias_6_alias_name", length = 100)
    private String individualAlias6AliasName;

    @Column(name = "individual_alias_7_quality", length = 100)
    private String individualAlias7Quality;

    @Column(name = "individual_alias_7_alias_name", length = 100)
    private String individualAlias7AliasName;

    @Column(name = "individual_alias_8_quality", length = 100)
    private String individualAlias8Quality;

    @Column(name = "individual_alias_8_alias_name", columnDefinition = "TEXT")
    private String individualAlias8AliasName;

    @Column(name = "individual_alias_9_quality", length = 100)
    private String individualAlias9Quality;

    @Column(name = "individual_alias_9_alias_name", length = 100)
    private String individualAlias9AliasName;

    @Column(name = "individual_alias_10_quality", length = 100)
    private String individualAlias10Quality;

    public static EntityClass1 create(final Map<String, Object> row) {
        final EntityClass1 entityClass1 = new EntityClass1();

        entityClass1.setDataid(getString(row, "DATAID"));
        entityClass1.setVerB670sionnum(getString(row, "VER+B670SIONNUM"));
        entityClass1.setFirstName(getString(row, "FIRST_NAME"));
        entityClass1.setSecondName(getString(row, "SECOND_NAME"));
        entityClass1.setThirdName(getString(row, "THIRD_NAME"));
        entityClass1.setUnListType(getString(row, "UN_LIST_TYPE"));
        entityClass1.setReferenceNumber(getString(row, "REFERENCE_NUMBER"));
        entityClass1.setListedOn(getString(row, "LISTED_ON"));
        entityClass1.setNameOriginalScript(getString(row, "NAME_ORIGINAL_SCRIPT"));
        entityClass1.setComments1(getString(row, "COMMENTS1"));
        entityClass1.setTitleValue0(getString(row, "TITLE/VALUE/0"));
        entityClass1.setDesignationValue0(getString(row, "DESIGNATION/VALUE/0"));
        entityClass1.setDesignationValue1(getString(row, "DESIGNATION/VALUE/1"));
        entityClass1.setNationalityValue0(getString(row, "NATIONALITY/VALUE/0"));
        entityClass1.setListTypeValue0(getString(row, "LIST_TYPE/VALUE/0"));
        entityClass1.setLastDayUpdatedValue0(getString(row, "LAST_DAY_UPDATED/VALUE/0"));
        entityClass1.setLastDayUpdatedValue1(getString(row, "LAST_DAY_UPDATED/VALUE/1"));
        entityClass1.setLastDayUpdatedValue2(getString(row, "LAST_DAY_UPDATED/VALUE/2"));
        entityClass1.setLastDayUpdatedValue3(getString(row, "LAST_DAY_UPDATED/VALUE/3"));
        entityClass1.setIndividualAlias0Quality(getString(row, "INDIVIDUAL_ALIAS/0/QUALITY"));
        entityClass1.setIndividualAlias0AliasName(getString(row, "INDIVIDUAL_ALIAS/0/ALIAS_NAME"));
        entityClass1.setIndividualAlias1Quality(getString(row, "INDIVIDUAL_ALIAS/1/QUALITY"));
        entityClass1.setIndividualAlias1AliasName(getString(row, "INDIVIDUAL_ALIAS/1/ALIAS_NAME"));
        entityClass1.setIndividualAlias2Quality(getString(row, "INDIVIDUAL_ALIAS/2/QUALITY"));
        entityClass1.setIndividualAlias2AliasName(getString(row, "INDIVIDUAL_ALIAS/2/ALIAS_NAME"));
        entityClass1.setIndividualAlias3Quality(getString(row, "INDIVIDUAL_ALIAS/3/QUALITY"));
        entityClass1.setIndividualAlias3AliasName(getString(row, "INDIVIDUAL_ALIAS/3/ALIAS_NAME"));
        entityClass1.setIndividualAddress0Country(getString(row, "INDIVIDUAL_ADDRESS/0/COUNTRY"));
        entityClass1.setIndividualDateOfBirth0TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/0/TYPE_OF_DATE"));
        entityClass1.setIndividualDateOfBirth0Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/0/YEAR"));
        entityClass1.setIndividualPlaceOfBirth0City(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/0/CITY"));
        entityClass1.setIndividualPlaceOfBirth0StateProvince(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/0/STATE_PROVINCE"));
        entityClass1.setIndividualPlaceOfBirth0Country(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/0/COUNTRY"));
        entityClass1.setIndividualDocument0(getString(row, "INDIVIDUAL_DOCUMENT_0"));
        entityClass1.setSortKey(getString(row, "SORT_KEY"));
        entityClass1.setSortKeyLastMod(getString(row, "SORT_KEY_LAST_MOD"));
        entityClass1.setLastDayUpdatedValue4(getString(row, "LAST_DAY_UPDATED/VALUE/4"));
        entityClass1.setLastDayUpdatedValue5(getString(row, "LAST_DAY_UPDATED/VALUE/5"));
        entityClass1.setIndividualPlaceOfBirth1City(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/1/CITY"));
        entityClass1.setIndividualPlaceOfBirth1StateProvince(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/1/STATE_PROVINCE"));
        entityClass1.setIndividualPlaceOfBirth1Country(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/1/COUNTRY"));
        entityClass1.setDesignationValue2(getString(row, "DESIGNATION/VALUE/2"));
        entityClass1.setLastDayUpdatedValue6(getString(row, "LAST_DAY_UPDATED/VALUE/6"));
        entityClass1.setIndividualDateOfBirth1TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/1/TYPE_OF_DATE"));
        entityClass1.setIndividualDateOfBirth1Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/1/YEAR"));
        entityClass1.setIndividualDateOfBirth2TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/2/TYPE_OF_DATE"));
        entityClass1.setIndividualDateOfBirth2Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/2/YEAR"));
        entityClass1.setIndividualPlaceOfBirth1Street(getString(row, "INDIVIDUAL_PLACE_OF_BIRTH/1/STREET"));
        entityClass1.setTitleValue1(getString(row, "TITLE/VALUE/1"));
        entityClass1.setIndividualDocument0TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/0/TYPE_OF_DOCUMENT"));
        entityClass1.setIndividualDocument0TypeOfDocument2(getString(row, "INDIVIDUAL_DOCUMENT/0/TYPE_OF_DOCUMENT2"));
        entityClass1.setIndividualDocument0Number(getString(row, "INDIVIDUAL_DOCUMENT/0/NUMBER"));
        entityClass1.setIndividualDocument1TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/1/TYPE_OF_DOCUMENT"));
        entityClass1.setIndividualDocument1TypeOfDocument2(getString(row, "INDIVIDUAL_DOCUMENT/1/TYPE_OF_DOCUMENT2"));
        entityClass1.setIndividualDocument1Number(getString(row, "INDIVIDUAL_DOCUMENT/1/NUMBER"));
        entityClass1.setGender(getString(row, "GENDER"));
        entityClass1.setIndividualAlias4Quality(getString(row, "INDIVIDUAL_ALIAS/4/QUALITY"));
        entityClass1.setIndividualAlias4AliasName(getString(row, "INDIVIDUAL_ALIAS/4/ALIAS_NAME"));
        entityClass1.setIndividualAlias4Note(getString(row, "INDIVIDUAL_ALIAS/4/NOTE"));
        entityClass1.setIndividualAlias5Quality(getString(row, "INDIVIDUAL_ALIAS/5/QUALITY"));
        entityClass1.setIndividualAlias5AliasName(getString(row, "INDIVIDUAL_ALIAS/5/ALIAS_NAME"));
        entityClass1.setIndividualAlias6Quality(getString(row, "INDIVIDUAL_ALIAS/6/QUALITY"));
        entityClass1.setIndividualAlias6AliasName(getString(row, "INDIVIDUAL_ALIAS/6/ALIAS_NAME"));
        entityClass1.setIndividualAlias7Quality(getString(row, "INDIVIDUAL_ALIAS/7/QUALITY"));
        entityClass1.setIndividualAlias7AliasName(getString(row, "INDIVIDUAL_ALIAS/7/ALIAS_NAME"));
        entityClass1.setIndividualAlias8Quality(getString(row, "INDIVIDUAL_ALIAS/8/QUALITY"));
        entityClass1.setIndividualAlias8AliasName(getString(row, "INDIVIDUAL_ALIAS/8/ALIAS_NAME"));
        entityClass1.setIndividualAlias9Quality(getString(row, "INDIVIDUAL_ALIAS/9/QUALITY"));
        entityClass1.setIndividualAlias9AliasName(getString(row, "INDIVIDUAL_ALIAS/9/ALIAS_NAME"));
        entityClass1.setIndividualAlias10Quality(getString(row, "INDIVIDUAL_ALIAS/10/QUALITY"));
        entityClass1.setStatus(Status.ACTIVE);
        entityClass1.setCreatedAt(LocalDateTime.now());

        return entityClass1;
    }
    private static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        return (value != null) ? value.toString().trim() : "";
    }
}
