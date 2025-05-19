package com.ponsun.san.searchLifcycle.search.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.searchLifcycle.search.request.CreateSearchRequest;
import com.ponsun.san.searchLifcycle.search.request.UpdateSearchRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Accessors(chain = true)
@Table(name = "search")
public class Search extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "matching_score")
    private Double matching_score;

    @Column(name = "list_id")
    private Integer listId;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "identity")
    private String identity;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "kycId")
    private Integer kycId;

    @Column(name = "isBulkSearch")
    private Integer isBulkSearch;

    @Column(name = "applicantFormId")
    private Integer applicantFormId;

    @Column(name = "isScreening")
    private Integer isScreening;

    @Column(name = "screeningType")
    private Integer screeningType;

    @Column(name = "isTaskAssigned")
    private Integer isTaskAssigned;

    @Column(name = "entryType")
    private Integer entryType;

    @Column(name = "accountNumber")
    private String accountNumber;

    public static Search create(final CreateSearchRequest createSearchRequest) {
        final Search search = new Search();
        search.setName(createSearchRequest.getName());
        search.setMatching_score(createSearchRequest.getMatching_score());
        search.setListId(createSearchRequest.getListId());
        search.setTypeId(createSearchRequest.getTypeId());
        search.setStateId(createSearchRequest.getStateId());
        search.setCountryId(createSearchRequest.getCountryId());
        search.setIdentity(createSearchRequest.getIdentity());
        search.setLevelId(createSearchRequest.getLevelId());
        search.setUid(createSearchRequest.getUid());
        search.setValid(createSearchRequest.getValid());
        search.setKycId(createSearchRequest.getKycId());
        search.setIsBulkSearch(createSearchRequest.getIsBulkSearch());
        search.setApplicantFormId(createSearchRequest.getApplicantFormId());
        search.setIsScreening(createSearchRequest.getIsScreening());
        search.setScreeningType(createSearchRequest.getScreeningType());
        search.setIsTaskAssigned(1);
        search.setEntryType(createSearchRequest.getEntryType());
        search.setAccountNumber(createSearchRequest.getAccountNumber());
        search.setStatus(Status.ACTIVE);
        search.onCreate();
        return search;
    }

    public void update(final UpdateSearchRequest updateSearchRequest) {
        this.setName(updateSearchRequest.getName());
        this.setMatching_score(updateSearchRequest.getMatching_score());
        this.setListId(updateSearchRequest.getListId());
        this.setTypeId(updateSearchRequest.getTypeId());
        this.setStateId(updateSearchRequest.getStateId());
        this.setCountryId(updateSearchRequest.getCountryId());
        this.setIdentity(updateSearchRequest.getIdentity());
        this.setLevelId(updateSearchRequest.getLevelId());
        this.setUid(updateSearchRequest.getUid());
        this.setValid(updateSearchRequest.getValid());
        this.setKycId(updateSearchRequest.getKycId());
        this.setIsBulkSearch(updateSearchRequest.getIsBulkSearch());
        this.setIsScreening(updateSearchRequest.getIsScreening());
        this.setApplicantFormId(updateSearchRequest.getApplicantFormId());
        this.setScreeningType(updateSearchRequest.getScreeningType());
        this.setIsTaskAssigned(updateSearchRequest.getIsTaskAssigned());
        this.setEntryType(updateSearchRequest.getEntryType());
        this.setAccountNumber(updateSearchRequest.getAccountNumber());
        this.setStatus(Status.ACTIVE);
        this.onUpdate();
    }
}
