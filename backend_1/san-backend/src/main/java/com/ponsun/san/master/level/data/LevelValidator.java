package com.ponsun.san.master.level.data;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.master.level.request.CreateLevelRequest;
import com.ponsun.san.master.level.request.UpdateLevelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LevelValidator {
    public void validateSaveLevelData(final CreateLevelRequest request){
        if(request.getName() == null || request.getName().equals("")){
            throw new EQAS_SAN_ApplicationException("Name parameter required");
        }
    }
    public void validateUpdateLevelData(final UpdateLevelRequest request){
        if(request.getName() == null || request.getName().equals("")){
            throw new EQAS_SAN_ApplicationException("Name parameter required");
        }
    }
}
