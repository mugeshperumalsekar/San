package com.ponsun.san.master.statusMapping.data;

import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;

import com.ponsun.san.master.statusMapping.request.CreateStatusMappingRequest;
import com.ponsun.san.master.statusMapping.request.UpdateStatusMappingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatusMappingValidator {

    public void validateSaveStatus(final CreateStatusMappingRequest request) {
        if (request.getStatusId() == null || request.getStatusId().equals("")) {
            throw new EQAS_SAN_ApplicationException("Status parameter required");
        }
    }
    public void validateUpdateStatus(final UpdateStatusMappingRequest request) {
        if (request.getStatusId() == null || request.getStatusId().equals("")) {
            throw new EQAS_SAN_ApplicationException("Status parameter required");
        }
    }
}
