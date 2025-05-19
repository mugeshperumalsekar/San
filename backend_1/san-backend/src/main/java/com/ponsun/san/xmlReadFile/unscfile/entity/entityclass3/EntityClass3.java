package com.ponsun.san.xmlReadFile.unscfile.entity.entityclass3;

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
@Table(name = "individual_3")
public class EntityClass3 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(mappedBy = "entityClass3")
    private EntityClass1 entityClass1;

    @Column(name = "dataid", length = 100)
    private String dataid;

    @Column(name = "individual_date_of_birth_8_type_of_date", length = 100)
    private String individualDateOfBirth8TypeOfDate;

    @Column(name = "individual_date_of_birth_8_year", length = 100)
    private String individualDateOfBirth8Year;

    @Column(name = "individual_document_3_type_of_document", length = 100)
    private String individualDocument3TypeOfDocument;

    @Column(name = "individual_document_3_type_of_document2", length = 100)
    private String individualDocument3TypeOfDocument2;

    @Column(name = "individual_document_3_number", length = 100)
    private String individualDocument3Number;

    @Column(name = "individual_document_3_issuing_country", length = 100)
    private String individualDocument3IssuingCountry;

    @Column(name = "individual_address_1_state_province", length = 100)
    private String individualAddress1StateProvince;

    @Column(name = "individual_address_1_country", length = 100)
    private String individualAddress1Country;

    @Column(name = "individual_address_1_note", length = 100)
    private String individualAddress1Note;

    @Column(name = "individual_alias_16_quality", length = 100)
    private String individualAlias16Quality;

    @Column(name = "individual_alias_16_alias_name", length = 100)
    private String individualAlias16AliasName;

    @Column(name = "individual_alias_17_quality", length = 100)
    private String individualAlias17Quality;

    @Column(name = "individual_alias_17_alias_name", length = 100)
    private String individualAlias17AliasName;

    @Column(name = "individual_alias_18_quality", length = 100)
    private String individualAlias18Quality;

    @Column(name = "individual_alias_18_alias_name", length = 100)
    private String individualAlias18AliasName;

    @Column(name = "individual_alias_19_quality", length = 100)
    private String individualAlias19Quality;

    @Column(name = "individual_alias_19_alias_name", length = 100)
    private String individualAlias19AliasName;

    @Column(name = "individual_alias_20_quality", length = 100)
    private String individualAlias20Quality;

    @Column(name = "individual_alias_20_alias_name", length = 100)
    private String individualAlias20AliasName;

    @Column(name = "individual_alias_21_quality", length = 100)
    private String individualAlias21Quality;

    @Column(name = "individual_alias_21_alias_name", length = 100)
    private String individualAlias21AliasName;

    @Column(name = "individual_alias_22_quality", length = 100)
    private String individualAlias22Quality;

    @Column(name = "individual_alias_22_alias_name", length = 100)
    private String individualAlias22AliasName;

    @Column(name = "individual_address_1_street", length = 100)
    private String individualAddress1Street;

    @Column(name = "individual_address_2_street", length = 100)
    private String individualAddress2Street;

    @Column(name = "individual_address_2_country", length = 100)
    private String individualAddress2Country;

    @Column(name = "individual_document_2_date_of_issue", length = 100)
    private String individualDocument2DateOfIssue;

    @Column(name = "individual_document_2_city_of_issue", length = 100)
    private String individualDocument2CityOfIssue;

    @Column(name = "individual_document_3_date_of_issue", length = 100)
    private String individualDocument3DateOfIssue;

    @Column(name = "individual_document_3_note", length = 100)
    private String individualDocument3Note;

    @Column(name = "individual_document_4_type_of_document", length = 100)
    private String individualDocument4TypeOfDocument;

    @Column(name = "individual_document_4_number", length = 100)
    private String individualDocument4Number;

    @Column(name = "individual_document_4_issuing_country", length = 100)
    private String individualDocument4IssuingCountry;

    @Column(name = "individual_document_4_date_of_issue", length = 100)
    private String individualDocument4DateOfIssue;

    @Column(name = "individual_document_4_city_of_issue", length = 100)
    private String individualDocument4CityOfIssue;

    @Column(name = "individual_document_5_type_of_document", length = 100)
    private String individualDocument5TypeOfDocument;

    @Column(name = "individual_document_5_number", length = 100)
    private String individualDocument5Number;

    @Column(name = "individual_document_5_issuing_country", length = 100)
    private String individualDocument5IssuingCountry;

    @Column(name = "individual_document_5_date_of_issue", length = 100)
    private String individualDocument5DateOfIssue;

    @Column(name = "individual_document_5_city_of_issue", length = 100)
    private String individualDocument5CityOfIssue;

    @Column(name = "individual_document_6_type_of_document", length = 100)
    private String individualDocument6TypeOfDocument;

    @Column(name = "individual_document_6_number", length = 100)
    private String individualDocument6Number;

    @Column(name = "individual_document_6_date_of_issue", length = 100)
    private String individualDocument6DateOfIssue;

    @Column(name = "individual_document_6_city_of_issue", length = 100)
    private String individualDocument6CityOfIssue;

    @Column(name = "individual_document_7_type_of_document", length = 100)
    private String individualDocument7TypeOfDocument;

    @Column(name = "individual_document_7_number", length = 100)
    private String individualDocument7Number;

    @Column(name = "individual_document_7_issuing_country", length = 100)
    private String individualDocument7IssuingCountry;

    @Column(name = "individual_document_7_date_of_issue", length = 100)
    private String individualDocument7DateOfIssue;

    @Column(name = "individual_document_7_city_of_issue", length = 100)
    private String individualDocument7CityOfIssue;

    @Column(name = "individual_document_8_type_of_document", length = 100)
    private String individualDocument8TypeOfDocument;

    @Column(name = "individual_document_8_number", length = 100)
    private String individualDocument8Number;

    @Column(name = "individual_document_8_date_of_issue", length = 100)
    private String individualDocument8DateOfIssue;

    @Column(name = "individual_document_8_city_of_issue", length = 100)
    private String individualDocument8CityOfIssue;

    @Column(name = "individual_document_8_note", length = 100)
    private String individualDocument8Note;

    @Column(name = "individual_document_9_type_of_document", length = 100)
    private String individualDocument9TypeOfDocument;

    @Column(name = "individual_document_9_number", length = 100)
    private String individualDocument9Number;

    @Column(name = "individual_document_9_issuing_country", length = 100)
    private String individualDocument9IssuingCountry;

    @Column(name = "individual_document_9_date_of_issue", length = 100)
    private String individualDocument9DateOfIssue;

    @Column(name = "individual_document_9_city_of_issue", length = 100)
    private String individualDocument9CityOfIssue;

    @Column(name = "individual_document_9_note", length = 100)
    private String individualDocument9Note;

    @Column(name = "individual_document_10_type_of_document", length = 100)
    private String individualDocument10TypeOfDocument;

    @Column(name = "individual_document_10_number", length = 100)
    private String individualDocument10Number;

    @Column(name = "individual_document_10_note", length = 100)
    private String individualDocument10Note;

    @Column(name = "individual_document_11_type_of_document", length = 100)
    private String individualDocument11TypeOfDocument;

    @Column(name = "individual_document_11_number", length = 100)
    private String individualDocument11Number;

    @Column(name = "individual_document_11_note", length = 100)
    private String individualDocument11Note;

    @Column(name = "individual_document_12_type_of_document", length = 100)
    private String individualDocument12TypeOfDocument;

    @Column(name = "individual_document_12_number", length = 100)
    private String individualDocument12Number;

    @Column(name = "individual_document_12_note", length = 100)
    private String individualDocument12Note;

    @Column(name = "individual_document_13_type_of_document", length = 100)
    private String individualDocument13TypeOfDocument;

    @Column(name = "individual_document_13_number", length = 100)
    private String individualDocument13Number;

    @Column(name = "individual_alias_2_date_of_birth", length = 100)
    private String individualAlias2DateOfBirth;

    @Column(name = "individual_alias_3_date_of_birth", length = 100)
    private String individualAlias3DateOfBirth;

    @Column(name = "individual_alias_3_city_of_birth", length = 100)
    private String individualAlias3CityOfBirth;

    @Column(name = "individual_alias_3_country_of_birth", length = 100)
    private String individualAlias3CountryOfBirth;

    @Column(name = "individual_alias_4_date_of_birth", length = 100)
    private String individualAlias4DateOfBirth;


    public static EntityClass3 create(final Map<String, Object> row) {
        final EntityClass3 entityClass3 = new EntityClass3();

        entityClass3.setDataid(getString(row, "DATAID"));
        entityClass3.setIndividualDateOfBirth8TypeOfDate(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/8/TYPE_OF_DATE"));
        entityClass3.setIndividualDateOfBirth8Year(getString(row, "INDIVIDUAL_DATE_OF_BIRTH/8/YEAR"));
        entityClass3.setIndividualDocument3TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/3/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument3TypeOfDocument2(getString(row, "INDIVIDUAL_DOCUMENT/3/TYPE_OF_DOCUMENT2"));
        entityClass3.setIndividualDocument3Number(getString(row, "INDIVIDUAL_DOCUMENT/3/NUMBER"));
        entityClass3.setIndividualDocument3IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/3/ISSUING_COUNTRY"));
        entityClass3.setIndividualAddress1StateProvince(getString(row, "INDIVIDUAL_ADDRESS/1/STATE_PROVINCE"));
        entityClass3.setIndividualAddress1Country(getString(row, "INDIVIDUAL_ADDRESS/1/COUNTRY"));
        entityClass3.setIndividualAddress1Note(getString(row, "INDIVIDUAL_ADDRESS/1/NOTE"));
        entityClass3.setIndividualAlias16Quality(getString(row, "INDIVIDUAL_ALIAS/16/QUALITY"));
        entityClass3.setIndividualAlias16AliasName(getString(row, "INDIVIDUAL_ALIAS/16/ALIAS_NAME"));
        entityClass3.setIndividualAlias17Quality(getString(row, "INDIVIDUAL_ALIAS/17/QUALITY"));
        entityClass3.setIndividualAlias17AliasName(getString(row, "INDIVIDUAL_ALIAS/17/ALIAS_NAME"));
        entityClass3.setIndividualAlias18Quality(getString(row, "INDIVIDUAL_ALIAS/18/QUALITY"));
        entityClass3.setIndividualAlias18AliasName(getString(row, "INDIVIDUAL_ALIAS/18/ALIAS_NAME"));
        entityClass3.setIndividualAlias19Quality(getString(row, "INDIVIDUAL_ALIAS/19/QUALITY"));
        entityClass3.setIndividualAlias19AliasName(getString(row, "INDIVIDUAL_ALIAS/19/ALIAS_NAME"));
        entityClass3.setIndividualAlias20Quality(getString(row, "INDIVIDUAL_ALIAS/20/QUALITY"));
        entityClass3.setIndividualAlias20AliasName(getString(row, "INDIVIDUAL_ALIAS/20/ALIAS_NAME"));
        entityClass3.setIndividualAlias21Quality(getString(row, "INDIVIDUAL_ALIAS/21/QUALITY"));
        entityClass3.setIndividualAlias21AliasName(getString(row, "INDIVIDUAL_ALIAS/21/ALIAS_NAME"));
        entityClass3.setIndividualAlias22Quality(getString(row, "INDIVIDUAL_ALIAS/22/QUALITY"));
        entityClass3.setIndividualAlias22AliasName(getString(row, "INDIVIDUAL_ALIAS/22/ALIAS_NAME"));
        entityClass3.setIndividualAddress1Street(getString(row, "INDIVIDUAL_ADDRESS/1/STREET"));
        entityClass3.setIndividualAddress2Street(getString(row, "INDIVIDUAL_ADDRESS/2/STREET"));
        entityClass3.setIndividualAddress2Country(getString(row, "INDIVIDUAL_ADDRESS/2/COUNTRY"));
        entityClass3.setIndividualDocument2DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/2/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument2CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/2/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument3DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/3/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument3Note(getString(row, "INDIVIDUAL_DOCUMENT/3/NOTE"));
        entityClass3.setIndividualDocument4TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/4/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument4Number(getString(row, "INDIVIDUAL_DOCUMENT/4/NUMBER"));
        entityClass3.setIndividualDocument4IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/4/ISSUING_COUNTRY"));
        entityClass3.setIndividualDocument4DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/4/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument4CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/4/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument5TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/5/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument5Number(getString(row, "INDIVIDUAL_DOCUMENT/5/NUMBER"));
        entityClass3.setIndividualDocument5IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/5/ISSUING_COUNTRY"));
        entityClass3.setIndividualDocument5DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/5/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument5CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/5/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument6TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/6/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument6Number(getString(row, "INDIVIDUAL_DOCUMENT/6/NUMBER"));
        entityClass3.setIndividualDocument6DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/6/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument6CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/6/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument7TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/7/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument7Number(getString(row, "INDIVIDUAL_DOCUMENT/7/NUMBER"));
        entityClass3.setIndividualDocument7IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/7/ISSUING_COUNTRY"));
        entityClass3.setIndividualDocument7DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/7/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument7CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/7/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument8TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/8/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument8Number(getString(row, "INDIVIDUAL_DOCUMENT/8/NUMBER"));
        entityClass3.setIndividualDocument8DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/8/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument8CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/8/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument8Note(getString(row, "INDIVIDUAL_DOCUMENT/8/NOTE"));
        entityClass3.setIndividualDocument9TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/9/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument9Number(getString(row, "INDIVIDUAL_DOCUMENT/9/NUMBER"));
        entityClass3.setIndividualDocument9IssuingCountry(getString(row, "INDIVIDUAL_DOCUMENT/9/ISSUING_COUNTRY"));
        entityClass3.setIndividualDocument9DateOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/9/DATE_OF_ISSUE"));
        entityClass3.setIndividualDocument9CityOfIssue(getString(row, "INDIVIDUAL_DOCUMENT/9/CITY_OF_ISSUE"));
        entityClass3.setIndividualDocument9Note(getString(row, "INDIVIDUAL_DOCUMENT/9/NOTE"));
        entityClass3.setIndividualDocument10TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/10/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument10Number(getString(row, "INDIVIDUAL_DOCUMENT/10/NUMBER"));
        entityClass3.setIndividualDocument10Note(getString(row, "INDIVIDUAL_DOCUMENT/10/NOTE"));
        entityClass3.setIndividualDocument11TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/11/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument11Number(getString(row, "INDIVIDUAL_DOCUMENT/11/NUMBER"));
        entityClass3.setIndividualDocument11Note(getString(row, "INDIVIDUAL_DOCUMENT/11/NOTE"));
        entityClass3.setIndividualDocument12TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/12/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument12Number(getString(row, "INDIVIDUAL_DOCUMENT/12/NUMBER"));
        entityClass3.setIndividualDocument12Note(getString(row, "INDIVIDUAL_DOCUMENT/12/NOTE"));
        entityClass3.setIndividualDocument13TypeOfDocument(getString(row, "INDIVIDUAL_DOCUMENT/13/TYPE_OF_DOCUMENT"));
        entityClass3.setIndividualDocument13Number(getString(row, "INDIVIDUAL_DOCUMENT/13/NUMBER"));
        entityClass3.setIndividualAlias2DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/2/DATE_OF_BIRTH"));
        entityClass3.setIndividualAlias3DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/3/DATE_OF_BIRTH"));
        entityClass3.setIndividualAlias3CityOfBirth(getString(row, "INDIVIDUAL_ALIAS/3/CITY_OF_BIRTH"));
        entityClass3.setIndividualAlias3CountryOfBirth(getString(row, "INDIVIDUAL_ALIAS/3/COUNTRY_OF_BIRTH"));
        entityClass3.setIndividualAlias4DateOfBirth(getString(row, "INDIVIDUAL_ALIAS/4/DATE_OF_BIRTH"));
        entityClass3.setStatus(Status.ACTIVE);
        entityClass3.setCreatedAt(LocalDateTime.now());
        return entityClass3;
    }


    private static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        return (value != null) ? value.toString().trim() : "";
    }
}
