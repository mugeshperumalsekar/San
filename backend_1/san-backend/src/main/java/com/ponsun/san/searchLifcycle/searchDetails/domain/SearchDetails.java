package com.ponsun.san.searchLifcycle.searchDetails.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.searchLifcycle.searchDetails.request.CreateSearchDetailsRequest;
import com.ponsun.san.searchLifcycle.searchDetails.request.UpdateSearchDetailsRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(name ="search_details")
public class SearchDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="matching_score")
    private Double matching_score;

    @Column(name="list_id")
    private Integer listId;

    @Column(name="type_id")
    private Integer typeId;

    @Column(name="state_id")
    private Integer stateId;

    @Column(name="country_id")
    private Integer countryId;

    @Column(name="identity")
    private String identity;

    @Column(name="level_id")
    private Long levelId;

    @Column(name="uid")
    private Integer uid;

    @Column(name="valid")
    private Boolean valid;

    @Column(name="kycId")
    private Integer kycId;

    public static  SearchDetails create(final CreateSearchDetailsRequest createSearchDetailsRequest){
        final SearchDetails searchDetails = new SearchDetails();
        searchDetails.setId(createSearchDetailsRequest.getId());
        searchDetails.setName(createSearchDetailsRequest.getName());
        searchDetails.setMatching_score(createSearchDetailsRequest.getMatching_score());
        searchDetails.setListId(createSearchDetailsRequest.getListId());
        searchDetails.setTypeId(createSearchDetailsRequest.getTypeId());
        searchDetails.setStateId(createSearchDetailsRequest.getStateId());
        searchDetails.setCountryId(createSearchDetailsRequest.getCountryId());
        searchDetails.setIdentity(createSearchDetailsRequest.getIdentity());
        searchDetails.setLevelId(createSearchDetailsRequest.getLevelId());
        searchDetails.setUid(createSearchDetailsRequest.getUid());
        searchDetails.setValid(createSearchDetailsRequest.getValid());
        searchDetails.setKycId(createSearchDetailsRequest.getKycId());
        searchDetails.setStatus(Status.ACTIVE);
        searchDetails.onCreate();
        return searchDetails;
    }

    public void update(final UpdateSearchDetailsRequest updateSearchDetailsRequest){
        this.setName(updateSearchDetailsRequest.getName());
        this.setMatching_score(updateSearchDetailsRequest.getMatching_score());
        this.setListId(updateSearchDetailsRequest.getListId());
        this.setTypeId(updateSearchDetailsRequest.getTypeId());
        this.setStateId(updateSearchDetailsRequest.getStateId());
        this.setCountryId(updateSearchDetailsRequest.getCountryId());
        this.setIdentity(updateSearchDetailsRequest.getIdentity());
        this.setLevelId(updateSearchDetailsRequest.getLevelId());
        this.setUid(updateSearchDetailsRequest.getUid());
        this.setValid(updateSearchDetailsRequest.getValid());
        this.setKycId(updateSearchDetailsRequest.getKycId());
        this.setStatus(Status.ACTIVE);
        this.onUpdate();
    }
}
