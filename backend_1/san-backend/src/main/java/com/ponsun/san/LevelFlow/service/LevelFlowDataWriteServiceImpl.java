package com.ponsun.san.LevelFlow.service;

import com.ponsun.san.LevelFlow.data.LevelFlowWriteData;
import com.ponsun.san.infrastructure.utils.Response;
import com.ponsun.san.searchLifcycle.hitcase.domain.Hitcase;
import com.ponsun.san.searchLifcycle.hitcase.domain.HitcaseRepository;
import com.ponsun.san.searchLifcycle.hitcase.request.CreateHitCaseRequest;
import com.ponsun.san.searchLifcycle.hitcaselifecycle.domain.HitcaseLifecycle;
import com.ponsun.san.searchLifcycle.hitcaselifecycle.domain.HitcaseLifecycleRepository;
import com.ponsun.san.searchLifcycle.hitcaselifecycle.request.CreateHitCaseLifeCycleRequest;
import com.ponsun.san.searchLifcycle.hitrecordlifecycle.domain.HitRecordLifecycle;
import com.ponsun.san.searchLifcycle.hitrecordlifecycle.domain.HitRecordlifecycleRepository;
import com.ponsun.san.searchLifcycle.hitrecordlifecycle.request.CreateHitRecordLifecycle;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class LevelFlowDataWriteServiceImpl implements LevelFlowDataWriteService {
    private final HitRecordlifecycleRepository hitRecordlifecycleRepository;
    private final HitcaseRepository hitcaseRepository;
    private final HitcaseLifecycleRepository hitcaseLifecycleRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Response handleReview(LevelFlowWriteData levelFlowWriteData) {
        Integer statusId = levelFlowWriteData.getStatusId();
        Integer levelId = levelFlowWriteData.getLevel_id();
        Integer caseId = levelFlowWriteData.getCaseId();
        Integer hitId = levelFlowWriteData.getHitdata_id();

        if(hitId!=0)
        {
            String updateHitRecord = "UPDATE hitrecord  a SET a.statusNowId = :statusId, a.updated_at = NOW() WHERE a.id = :hitdata_id";
            entityManager.createNativeQuery(updateHitRecord)
                    .setParameter("statusId", statusId)
                    .setParameter("hitdata_id", hitId)
                    .executeUpdate();


            String updateHitRecordLifecycle = "UPDATE hitrecord_lifecycle  b SET b.valid = :valid, b.updated_at = NOW() WHERE b.hitdata_id  = :hitdata_id ";
            entityManager.createNativeQuery(updateHitRecordLifecycle)
                    .setParameter("valid", false) //
                    .setParameter("hitdata_id", hitId)
                    .executeUpdate();
        }
        if(caseId!=0)
        {
            String updateHitCase = "UPDATE hitcase  a SET a.status_now_id  = :statusId, a.updated_at = NOW() WHERE a.hit_id = :hitdata_id ";
            entityManager.createNativeQuery(updateHitCase)
                    .setParameter("statusId", statusId)
                    .setParameter("hitdata_id", hitId)
                    .executeUpdate();

            String updateCaseLifecycle = "UPDATE hitcase_lifecycle  b SET b.valid = :valid, b.updated_at = NOW() WHERE b.id  = :hitdata_id ";
            entityManager.createNativeQuery(updateCaseLifecycle)
                    .setParameter("valid", false) //
                    .setParameter("hitdata_id", hitId)
                    .executeUpdate();

        }

        if (levelId == 1) {
            handleHitRecordLifecycle(levelFlowWriteData, caseId);
        } else if (levelId == 2) {
            if(statusId==1)
                handleHitRecordLifecycle(levelFlowWriteData, caseId);
            if (statusId == 2) {

                CreateHitCaseRequest createHitCaseRequest = new CreateHitCaseRequest();
                createHitCaseRequest.setCriminalId(levelFlowWriteData.getCriminal_id());
                createHitCaseRequest.setHitId(levelFlowWriteData.getHitdata_id());
                createHitCaseRequest.setRemark(levelFlowWriteData.getRemark());
                createHitCaseRequest.setLevelId(levelFlowWriteData.getLevel_id());
                createHitCaseRequest.setSearchId(levelFlowWriteData.getSearch_id());
                createHitCaseRequest.setStatusNowId(levelFlowWriteData.getStatusId());
                createHitCaseRequest.setUid(levelFlowWriteData.getUid());

                final Hitcase hitcase = Hitcase.create(createHitCaseRequest);
                Hitcase resp_hitcase = this.hitcaseRepository.saveAndFlush(hitcase);
                caseId = resp_hitcase.getId();

                handleHitRecordLifecycle(levelFlowWriteData, caseId);
                handleHitCaseLifeCycle(levelFlowWriteData,caseId );
            }
        }
        else {

                handleHitRecordLifecycle(levelFlowWriteData, caseId);
                handleHitCaseLifeCycle(levelFlowWriteData, caseId);
            }

        return null;
    }
    private Integer handleHitRecordLifecycle(LevelFlowWriteData levelFlowWriteData, Integer caseId) {
        CreateHitRecordLifecycle createHitRecordLifecycle = toHitRecordLifecycle(levelFlowWriteData);
        HitRecordLifecycle hitRecordLifecycle = HitRecordLifecycle.create(createHitRecordLifecycle);
        hitRecordLifecycle.setCaseId(caseId);
        hitRecordLifecycle = this.hitRecordlifecycleRepository.saveAndFlush(hitRecordLifecycle);

        return hitRecordLifecycle.getId();

    }

    private Integer handleHitCaseLifeCycle(LevelFlowWriteData levelFlowWriteData, Integer caseId) {
        CreateHitCaseLifeCycleRequest createHitCaseLifeCycleRequest = toHitCaseLifecycle(levelFlowWriteData);
        HitcaseLifecycle hitcaseLifecycle = HitcaseLifecycle.create(createHitCaseLifeCycleRequest);
        hitcaseLifecycle.setCaseId(caseId);
        hitcaseLifecycle = this.hitcaseLifecycleRepository.saveAndFlush(hitcaseLifecycle);

        return hitcaseLifecycle.getId();
    }


    public static CreateHitRecordLifecycle toHitRecordLifecycle(LevelFlowWriteData data) {

        CreateHitRecordLifecycle createHitRecordLifecycle = new CreateHitRecordLifecycle();
        createHitRecordLifecycle.setCaseId(data.getCaseId());
        createHitRecordLifecycle.setHitId(data.getHitdata_id());
        createHitRecordLifecycle.setStatusId(data.getStatusId());
        createHitRecordLifecycle.setCriminalId(data.getCriminal_id());
        createHitRecordLifecycle.setIsAlive(data.getIsAlive());
        createHitRecordLifecycle.setLevelId(data.getLevel_id());
        createHitRecordLifecycle.setPassingLevelId(data.getPassingLevelId());
        createHitRecordLifecycle.setRemark(data.getRemark());
        createHitRecordLifecycle.setSearchId(data.getSearch_id());
        createHitRecordLifecycle.setValid(data.getValid());
        createHitRecordLifecycle.setUid(data.getUid());
        return createHitRecordLifecycle;
    }
    public static CreateHitCaseLifeCycleRequest toHitCaseLifecycle(LevelFlowWriteData data) {

        CreateHitCaseLifeCycleRequest createHitCaseLifeCycleRequest = new CreateHitCaseLifeCycleRequest();
        createHitCaseLifeCycleRequest.setCaseId(data.getCaseId());
        createHitCaseLifeCycleRequest.setLevelId(data.getLevel_id());
        createHitCaseLifeCycleRequest.setRemark(data.getRemark());
        createHitCaseLifeCycleRequest.setStatusId(data.getStatusId());
        createHitCaseLifeCycleRequest.setValid(data.getValid());
        createHitCaseLifeCycleRequest.setUid(data.getUid());
        return createHitCaseLifeCycleRequest;
    }

}
