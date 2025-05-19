package com.ponsun.san.fileType.services;

import com.ponsun.san.fileType.domain.FileType;
import com.ponsun.san.fileType.domain.FileTypeRepository;
import com.ponsun.san.fileType.domain.FileTypeRepositoryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class FileTypeReadPlatformServiceImpl implements FileTypeReadPlatformService{
    private final FileTypeRepository fileTypeRepository;
    private final JdbcTemplate jdbcTemplate;
    private final FileTypeRepositoryWrapper fileTypeRepositoryWrapper;

    @Override
    public FileType fetchFileTypeById(Integer id){return this.fileTypeRepository.findById(id).get();}
    @Override
    public List<FileType> fetchAllFileType(){return this.fileTypeRepository.findAll();}
}
