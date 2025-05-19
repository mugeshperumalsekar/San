package com.ponsun.san.fileType.domain;

import com.ponsun.san.fileType.request.AbstractFileTypeBaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileTypeRepositoryWrapper extends AbstractFileTypeBaseRequest {
    private final FileTypeRepository fileTypeRepository;

    @Transactional
    public FileType findOneWithNotFoundDetection(final Integer id){
      return this.fileTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("FileType Not found "+id));
    }
    @Override
    public String toString(){return super.toString();}
}
