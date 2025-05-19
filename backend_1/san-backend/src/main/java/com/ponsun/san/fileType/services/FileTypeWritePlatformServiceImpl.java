package com.ponsun.san.fileType.services;

import com.ponsun.san.common.entity.Status;
import com.ponsun.san.fileType.domain.FileType;
import com.ponsun.san.fileType.domain.FileTypeRepository;
import com.ponsun.san.fileType.domain.FileTypeRepositoryWrapper;
import com.ponsun.san.fileType.request.CreateFileTypeRequest;
import com.ponsun.san.fileType.request.UpdateFileTypeRequest;
import com.ponsun.san.infrastructure.exceptions.EQAS_SAN_ApplicationException;
import com.ponsun.san.infrastructure.utils.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileTypeWritePlatformServiceImpl implements FileTypeWritePlatformService{
    private final FileTypeRepository fileTypeRepository;
    private final FileTypeRepositoryWrapper fileTypeRepositoryWrapper;

    @Transactional
    public Response createFileType(CreateFileTypeRequest createFileTypeRequest){
        try{
           final FileType fileType = FileType.create(createFileTypeRequest);
           this.fileTypeRepository.saveAndFlush(fileType);
           return Response.of(Long.valueOf(fileType.getId()));
        }catch (DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException("Error creating FileType:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response updateFileType(Integer id, UpdateFileTypeRequest updateFileTypeRequest){
      try{
          final FileType fileType = this.fileTypeRepositoryWrapper.findOneWithNotFoundDetection(id);
          fileType.update(updateFileTypeRequest);
          this.fileTypeRepository.saveAndFlush(fileType);
          return Response.of(Long.valueOf(fileType.getId()));
      }catch (DataIntegrityViolationException e) {
          throw new EQAS_SAN_ApplicationException(e.getMessage());
      }
    }

    @Override
    @Transactional
    public Response deactive(Integer id,Integer euid){
        try{
            FileType fileType = this.fileTypeRepositoryWrapper.findOneWithNotFoundDetection(id);
            fileType.setEuid(euid);
            fileType.setStatus(Status.ACTIVE);
            fileType.setUpdatedAt(LocalDateTime.now());
            return Response.of(Long.valueOf(fileType.getId()));
        }catch (DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response blockFileType(Integer id){
        try{
            final FileType fileType = this.fileTypeRepositoryWrapper.findOneWithNotFoundDetection(id);
            fileType.setValid(false);
            fileType.setStatus(Status.DELETE);
            fileType.setUpdatedAt(LocalDateTime.now());
            this.fileTypeRepository.saveAndFlush(fileType);
            return Response.of(Long.valueOf(id));
        }catch (DataIntegrityViolationException e) {
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response unblockFileType(Integer id){
        try{
            final FileType fileType = this.fileTypeRepositoryWrapper.findOneWithNotFoundDetection(id);
            fileType.setValid(true);
            fileType.setStatus(Status.ACTIVE);
            fileType.setUpdatedAt(LocalDateTime.now());
            this.fileTypeRepository.saveAndFlush(fileType);
            return Response.of(Long.valueOf(id));
        }catch (DataIntegrityViolationException e){
            throw new EQAS_SAN_ApplicationException(e.getMessage());
        }
    }
}
