package com.ponsun.san.bulkTaskAssign.data;


import com.ponsun.san.bulkTaskAssign.request.CreateBulkTaskAssignRequest;
import com.ponsun.san.bulkTaskAssign.request.UpdateBulkTaskAssignRequest;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BulkTaskAssignValidator {
    public void validateSaveBulkTaskAssign(final CreateBulkTaskAssignRequest request){
        if(request.getAssignTo() == null || request.getAssignTo().equals("")){
            throw  new EQAS_SAN_ApplicationException("BulkTaskAssignTo parameter required");
        }
    }

    public void validateUpdateBulkTaskAssign(final UpdateBulkTaskAssignRequest request){
        if(request.getAssignTo() == null || request.getAssignTo().equals("")){
            throw new EQAS_SAN_ApplicationException("BulkTaskAssignTo parameter required");
        }
    }
}
