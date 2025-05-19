package com.ponsun.san.xmlReadFile.unscfile.domain;


import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "unsc_individuals")
public class Individual extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_id", nullable = false)
    private String dataId;

    @Column(name = "version_num")
    private String versionNum;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "third_name")
    private String thirdName;

    @Column(name = "fourth_name")
    private String fourthName;

    @Column(name = "name_original_script", columnDefinition = "TEXT")
    private String nameOriginalScript;

    @Column(name = "gender")
    private String gender;

    @Column(name = "un_list_type")
    private String unListType;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "listed_on")
    private String listedOn;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "sort_key")
    private String sortKey;

    @Column(name = "sort_key_last_mod")
    private String sortKeyLastMod;

    // Relationships
    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Designation> designations;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nationality> nationalities;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualAlias> individualAliases;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceOfBirth> placesOfBirth;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DateOfBirth> datesOfBirth;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LastUpdate> lastUpdates;

    public static Individual create(final Map<String, Object> row) {
        final Individual individual = new Individual();
       individual.setDataId(getString(row, "DATAID"));
       individual.setVersionNum(getString(row, "VER+B670SIONNUM"));
       individual.setFirstName(getString(row, "FIRST_NAME"));
       individual.setSecondName(getString(row, "SECOND_NAME"));
       individual.setThirdName(getString(row, "THIRD_NAME"));
       individual.setUnListType(getString(row, "UN_LIST_TYPE"));
       individual.setReferenceNumber(getString(row, "REFERENCE_NUMBER"));
       individual.setListedOn(getString(row, "LISTED_ON"));
       individual.setNameOriginalScript(getString(row, "NAME_ORIGINAL_SCRIPT"));
       individual.setComments(getString(row, "COMMENTS1"));
       individual.setSortKey(getString(row, "SORT_KEY"));
       individual.setSortKeyLastMod(getString(row, "SORT_KEY_LAST_MOD"));
       individual.setGender(getString(row, "GENDER"));
       individual.setStatus(Status.ACTIVE);
       individual.setCreatedAt(LocalDateTime.now());
        return individual;
    }
    private static String getString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        return (value != null) ? value.toString().trim() : "";
    }

    @Transactional
    public static Individual deactivateIndividual(Individual individualEntity) {
        if (individualEntity != null) {
            individualEntity.setStatus(Status.DELETE);
            individualEntity.setUpdatedAt(LocalDateTime.now());
            updateRelatedEntities(individualEntity.getTitles());
            updateRelatedEntities(individualEntity.getIndividualAliases());
            updateRelatedEntities(individualEntity.getDesignations());
            updateRelatedEntities(individualEntity.getAddresses());
            updateRelatedEntities(individualEntity.getDocuments());
            updateRelatedEntities(individualEntity.getNationalities());
            updateRelatedEntities(individualEntity.getPlacesOfBirth());
            updateRelatedEntities(individualEntity.getDatesOfBirth());
            updateRelatedEntities(individualEntity.getLastUpdates());
        }
        return individualEntity;
    }

    private static <T extends BaseEntity> void updateRelatedEntities(List<T> entities) {
        if (entities != null) {
            for (T entity : entities) {
                entity.setStatus(Status.DELETE);
                entity.setUpdatedAt(LocalDateTime.now());
            }
        }
    }
}
