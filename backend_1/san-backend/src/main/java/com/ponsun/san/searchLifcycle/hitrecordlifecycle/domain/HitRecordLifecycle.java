package com.ponsun.san.searchLifcycle.hitrecordlifecycle.domain;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.infrastructure.baseentity.BaseEntity;
import com.ponsun.san.searchLifcycle.hitcase.request.CreateHitCaseRequest;
import com.ponsun.san.searchLifcycle.hitrecordlifecycle.request.CreateHitRecordLifecycle;
import com.ponsun.san.searchLifcycle.hitrecordlifecycle.request.UpdateHitdataLifecycleRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Accessors(chain = true)
@Table(name = "hitrecord_lifecycle")
public class HitRecordLifecycle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * crm_search
     */
    @Column(name = "search_id")
    private Integer searchId;

    /**
     * crm_hitdata
     */

    @Column(name = "hitdata_id")
    private Integer hitdataId;

    @Column(name = "criminal_id")
    private Integer criminalId;

    /**
     * crm_hitcase
     */
    @Column(name = "case_id")
    private Integer caseId;

    @Column(name = "level_id")
    private Integer levelId;

    /**
     * crm_config_status
     */

    @Column(name = "statusId")
    private Integer statusId;

    @Column(name = "uid")
    private Integer uid;

//    @Column(name = "dt")
//    private String dt;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "remark")
    private String remark;

    @Column(name = "isAlive")
    private Integer isAlive;

    @Column(name = "passingLevelId")
    private Integer passingLevelId;

    public static HitRecordLifecycle create (final CreateHitRecordLifecycle createHitRecordLifecycle){
        final HitRecordLifecycle hitRecordLifecycle = new HitRecordLifecycle();
        hitRecordLifecycle.setSearchId(createHitRecordLifecycle.getSearchId());
        hitRecordLifecycle.setHitdataId(createHitRecordLifecycle.getHitId());
        hitRecordLifecycle.setCriminalId(createHitRecordLifecycle.getCriminalId());
        hitRecordLifecycle.setCaseId(createHitRecordLifecycle.getCaseId());
        hitRecordLifecycle.setLevelId(createHitRecordLifecycle.getLevelId());
        hitRecordLifecycle.setRemark(createHitRecordLifecycle.getRemark());
        hitRecordLifecycle.setStatusId(createHitRecordLifecycle.getStatusId());
        hitRecordLifecycle.setUid(createHitRecordLifecycle.getUid());
        hitRecordLifecycle.setValid(true);
        hitRecordLifecycle.setIsAlive(createHitRecordLifecycle.getIsAlive());
        hitRecordLifecycle.setPassingLevelId(createHitRecordLifecycle.getPassingLevelId());
        hitRecordLifecycle.setStatus(Status.ACTIVE);
        hitRecordLifecycle.onCreate();
        return hitRecordLifecycle;
    }

    public void update(final UpdateHitdataLifecycleRequest updateHitdataLifecycleRequest){
        this.setSearchId(updateHitdataLifecycleRequest.getSearchId());
        this.setHitdataId(updateHitdataLifecycleRequest.getHitId());
        this.setCriminalId(updateHitdataLifecycleRequest.getCriminalId());
        this.setCaseId(updateHitdataLifecycleRequest.getCaseId());
        this.setLevelId(updateHitdataLifecycleRequest.getLevelId());
        this.setStatusId(updateHitdataLifecycleRequest.getStatusId());
        this.setRemark(updateHitdataLifecycleRequest.getRemark());
        this.setValid(updateHitdataLifecycleRequest.getValid());
        this.setIsAlive(updateHitdataLifecycleRequest.getIsAlive());
        this.setPassingLevelId(updateHitdataLifecycleRequest.getPassingLevelId());
        this.onUpdate();
        this.setStatus(Status.ACTIVE);
    }
    // public static List<HitdataLifecycle> convertToEntities(Response response, CreateHitCaseRequest createHitdataLifecycles) {
    public static HitRecordLifecycle converter(Integer id, CreateHitCaseRequest createHitdataLifecycles) {
        HitRecordLifecycle entity = new HitRecordLifecycle();
        entity.setCaseId(id);
        entity.setCriminalId(createHitdataLifecycles.getCriminalId());
        entity.setHitdataId(createHitdataLifecycles.getHitId());
        entity.setSearchId(createHitdataLifecycles.getSearchId());
        entity.setLevelId(createHitdataLifecycles.getLevelId());
        entity.setStatusId(createHitdataLifecycles.getStatusNowId());
        entity.setRemark(createHitdataLifecycles.getRemark());
        entity.setUid(createHitdataLifecycles.getUid());
        entity.onCreate();
        return entity;
    }
}
