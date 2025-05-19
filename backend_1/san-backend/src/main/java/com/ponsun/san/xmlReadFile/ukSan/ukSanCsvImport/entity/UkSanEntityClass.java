package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.entity;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "sanctions_data_uk")
public class UkSanEntityClass extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "_last_updated")
    private String LastUpdated;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "ofsi_group_id")
    private String ofsiGroupId;

    @Column(name = "un_reference_number")
    private String unReferenceNumber;

    @Column(name = "name_6")
    private String name6;

    @Column(name = "name_1")
    private String name1;

    @Column(name = "name_2")
    private String name2;

    @Column(name = "name_3")
    private String name3;

    @Column(name = "name_4")
    private String name4;

    @Column(name = "name_5")
    private String name5;

    @Column(name = "name_type")
    private String nameType;

    @Column(name = "alias_strength")
    private String aliasStrength;

    @Column(name = "title")
    private String title;

    @Column(name = "name_non_latin_script", columnDefinition = "TEXT")
    private String nameNonLatinScript;

    @Column(name = "non_latin_script_type")
    private String nonLatinScriptType;

    @Column(name = "non_latin_script_language")
    private String nonLatinScriptLanguage;

    @Column(name = "regime_name")
    private String regimeName;

    @Column(name = "_individual")
    private String Individual;

    @Column(name = "_entity")
    private String Entity;

    @Column(name = "_ship_")
    private String Ship;

    @Column(name = "designation_source")
    private String designationSource;

    @Column(name = "sanctions_imposed", columnDefinition = "TEXT")
    private String sanctionsImposed;

    @Column(name = "other_information", columnDefinition = "TEXT")
    private String otherInformation;

    @Column(name = "uk_statement_of_reasons", columnDefinition = "TEXT")
    private String ukStatementOfReasons;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "address_line_3")
    private String addressLine3;

    @Column(name = "address_line_4")
    private String addressLine4;

    @Column(name = "address_line_5")
    private String addressLine5;

    @Column(name = "address_line_6")
    private String addressLine6;

    @Column(name = "address_postal_code")
    private String addressPostalCode;

    @Column(name = "address_country")
    private String addressCountry;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "website")
    private String website;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "date_designated")
    private String dateDesignated;

    @Column(name = "d_o_b")
    private String dOB;

    @Column(name = "nationality__ies_")
    private String nationalityIes;

    @Column(name = "national_identifier_number")
    private String nationalIdentifierNumber;

    @Column(name = "national_identifier_additional_information", columnDefinition = "TEXT")
    private String nationalIdentifierAdditionalInformation;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_additional_information", columnDefinition = "TEXT")
    private String passportAdditionalInformation;

    @Column(name = "position", columnDefinition = "TEXT")
    private String position;

    @Column(name = "gender")
    private String gender;

    @Column(name = "town_of_birth")
    private String townOfBirth;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "type_of_entity")
    private String typeOfEntity;

    @Column(name = "subsidiaries", columnDefinition = "TEXT")
    private String subsidiaries;

    @Column(name = "parent_company")
    private String parentCompany;

    @Column(name = "business_registration_number__s_")
    private String businessRegistrationNumberS;

    @Column(name = "imo_number")
    private String imoNumber;

    @Column(name = "current_owner_operator__s_")
    private String currentOwnerOperatorS;

    @Column(name = "previous_owner_operator__s_")
    private String previousOwnerOperatorS;

    @Column(name = "current_believed_flag_of_ship")
    private String currentBelievedFlagOfShip;

    @Column(name = "previous_flags")
    private String previousFlags;

    @Column(name = "type_of_ship")
    private String typeOfShip;

    @Column(name = "tonnage_of_ship")
    private String tonnageOfShip;

    @Column(name = "length_of_ship")
    private String lengthOfShip;

    @Column(name = "year_built")
    private String yearBuilt;

    @Column(name = "hull_identification_number__hin_")
    private String hullIdentificationNumberHin;

    public static UkSanEntityClass create(final Map<String, Object> row) {
        final UkSanEntityClass ukSanEntityClass = new UkSanEntityClass();

        ukSanEntityClass.setLastUpdated(getString(row, "Last Updated"));
        ukSanEntityClass.setUniqueId(getString(row, "Unique ID"));
        ukSanEntityClass.setOfsiGroupId(getString(row, "OFSI Group ID"));
        ukSanEntityClass.setUnReferenceNumber(getString(row, "UN Reference Number"));
        ukSanEntityClass.setName6(getString(row, "Name 6"));
        ukSanEntityClass.setName1(getString(row, "Name 1"));
        ukSanEntityClass.setName2(getString(row, "Name 2"));
        ukSanEntityClass.setName3(getString(row, "Name 3"));
        ukSanEntityClass.setName4(getString(row, "Name 4"));
        ukSanEntityClass.setName5(getString(row, "Name 5"));
        ukSanEntityClass.setNameType(getString(row, "Name type"));
        ukSanEntityClass.setAliasStrength(getString(row, "IndividualAlias strength"));
        ukSanEntityClass.setTitle(getString(row, "Title"));
        ukSanEntityClass.setNameNonLatinScript(getString(row, "Name non-latin script"));
        ukSanEntityClass.setNonLatinScriptType(getString(row, "Non-latin script type"));
        ukSanEntityClass.setNonLatinScriptLanguage(getString(row, "Non-latin script language"));
        ukSanEntityClass.setRegimeName(getString(row, "Regime Name"));
        ukSanEntityClass.setIndividual(getString(row, "Individual, Entity, Ship"));
        ukSanEntityClass.setEntity(getString(row, "Entity"));
        ukSanEntityClass.setShip(getString(row, "Ship"));
        ukSanEntityClass.setDesignationSource(getString(row, "Designation source"));
        ukSanEntityClass.setSanctionsImposed(getString(row, "Sanctions Imposed"));
        ukSanEntityClass.setOtherInformation(getString(row, "Other Information"));
        ukSanEntityClass.setUkStatementOfReasons(getString(row, "UK Statement of Reasons"));
        ukSanEntityClass.setAddressLine1(getString(row, "Address Line 1"));
        ukSanEntityClass.setAddressLine2(getString(row, "Address Line 2"));
        ukSanEntityClass.setAddressLine3(getString(row, "Address Line 3"));
        ukSanEntityClass.setAddressLine4(getString(row, "Address Line 4"));
        ukSanEntityClass.setAddressLine5(getString(row, "Address Line 5"));
        ukSanEntityClass.setAddressLine6(getString(row, "Address Line 6"));
        ukSanEntityClass.setAddressPostalCode(getString(row, "Address Postal Code"));
        ukSanEntityClass.setAddressCountry(getString(row, "Address Country"));
        ukSanEntityClass.setPhoneNumber(getString(row, "Phone number"));
        ukSanEntityClass.setWebsite(getString(row, "Website"));
        ukSanEntityClass.setEmailAddress(getString(row, "Email address"));
        ukSanEntityClass.setDateDesignated(getString(row, "Date Designated"));
        ukSanEntityClass.setDOB(getString(row, "D.O.B"));
        ukSanEntityClass.setNationalityIes(getString(row, "Nationality(/ies)"));
        ukSanEntityClass.setNationalIdentifierNumber(getString(row, "National Identifier number"));
        ukSanEntityClass.setNationalIdentifierAdditionalInformation(getString(row, "National Identifier additional information"));
        ukSanEntityClass.setPassportNumber(getString(row, "Passport number"));
        ukSanEntityClass.setPassportAdditionalInformation(getString(row, "Passport additional information"));
        ukSanEntityClass.setPosition(getString(row, "Position"));
        ukSanEntityClass.setGender(getString(row, "Gender"));
        ukSanEntityClass.setTownOfBirth(getString(row, "Town of birth"));
        ukSanEntityClass.setCountryOfBirth(getString(row, "Country of birth"));
        ukSanEntityClass.setTypeOfEntity(getString(row, "Type of entity"));
        ukSanEntityClass.setSubsidiaries(getString(row, "Subsidiaries"));
        ukSanEntityClass.setParentCompany(getString(row, "Parent company"));
        ukSanEntityClass.setBusinessRegistrationNumberS(getString(row, "Business registration number (s)"));
        ukSanEntityClass.setImoNumber(getString(row, "IMO number"));
        ukSanEntityClass.setCurrentOwnerOperatorS(getString(row, "Current owner/operator (s)"));
        ukSanEntityClass.setPreviousOwnerOperatorS(getString(row, "Previous owner/operator (s)"));
        ukSanEntityClass.setCurrentBelievedFlagOfShip(getString(row, "Current believed flag of ship"));
        ukSanEntityClass.setPreviousFlags(getString(row, "Previous flags"));
        ukSanEntityClass.setTypeOfShip(getString(row, "Type of ship"));
        ukSanEntityClass.setTonnageOfShip(getString(row, "Tonnage of ship"));
        ukSanEntityClass.setLengthOfShip(getString(row, "Length of ship"));
        ukSanEntityClass.setYearBuilt(getString(row, "Year Built"));
        ukSanEntityClass.setHullIdentificationNumberHin(getString(row, "Hull identification number (HIN)"));
        ukSanEntityClass.setStatus(Status.ACTIVE);
        ukSanEntityClass.setCreatedAt(LocalDateTime.now());
        return ukSanEntityClass;
    }

    private static String getString(Map<String, Object> row, String key) {

        for (String existingKey : row.keySet()) {
            String normalizedKey = existingKey.trim().replace("\uFEFF", ""); // Remove BOM if present
            if (normalizedKey.equalsIgnoreCase(key.trim())) {
                Object value = row.get(existingKey);
                return (value != null) ? value.toString().trim() : "";
            }
        }
        return "";
    }

}
