package com.ponsun.san.bulkAssignMapping.data;


import com.ponsun.san.bulkAssignMapping.request.CreateBulkAssignMappingRequest;
import com.ponsun.san.bulkAssignMapping.request.UpdateBulkAssignMappingRequest;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BulkAssignMappingValidator {
    public void validateSaveBulkAssignMapping(final CreateBulkAssignMappingRequest request){
        if(request.getSearchId() == null || request.getSearchId().equals("")){
            throw  new EQAS_SAN_ApplicationException("BulkAssignMappingTo parameter required");
        }
        if(request.getSearchId() == null || request.getSearchId().equals("")){
            throw new EQAS_SAN_ApplicationException("BulkAssignMappingBy parameter required");
        }
    }

    public void validateUpdateBulkAssignMapping(final UpdateBulkAssignMappingRequest request){
        if(request.getSearchId() == null || request.getSearchId().equals("")){
            throw new EQAS_SAN_ApplicationException("BulkAssignMappingTo parameter required");
        }
        if(request.getSearchId() == null || request.getSearchId().equals("")){
            throw new EQAS_SAN_ApplicationException("BulkAssignMappingBy parameter required");
        }
    }
}
