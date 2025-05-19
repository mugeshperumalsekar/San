package com.ponsun.san.fileType.api;

import com.ponsun.san.fileType.domain.FileType;
import com.ponsun.san.fileType.request.CreateFileTypeRequest;
import com.ponsun.san.fileType.request.UpdateFileTypeRequest;
import com.ponsun.san.fileType.services.FileTypeReadPlatformServiceImpl;
import com.ponsun.san.fileType.services.FileTypeWritePlatformServiceImpl;
import com.ponsun.san.infrastructure.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/fileType")
@Tag(name="FileTypeApiResource")
public class FileTypeApiResource {

    private final FileTypeWritePlatformServiceImpl fileTypeWritePlatformService;
    private final FileTypeReadPlatformServiceImpl fileTypeReadPlatformService;

    @PostMapping("/createFileType")
    public Response saveFileType(@RequestBody CreateFileTypeRequest createFileTypeRequest){
        Response response = this.fileTypeWritePlatformService.createFileType(createFileTypeRequest);
        return response;
    }

    @GetMapping
    public List<FileType> fetchAll(){
     return this.fileTypeReadPlatformService.fetchAllFileType();
    }
    @GetMapping("/{id}")
    public FileType fetchFileTypeById(@PathVariable Integer id){
        return this.fileTypeReadPlatformService.fetchFileTypeById(id);
    }

    @PutMapping("/{id}")
    public Response updateFileType(@PathVariable Integer id, @RequestBody UpdateFileTypeRequest updateFileTypeRequest){
        Response response = this.fileTypeWritePlatformService.updateFileType(id,updateFileTypeRequest);
        return response;
    }

    @PutMapping("/deactive/{id}")
    public Response deactive(@PathVariable Integer id,Integer euid){
      Response response = this.fileTypeWritePlatformService.deactive(id,euid);
      return response;
    }

    @PutMapping("/block/{id}")
    public Response blockFileType(@PathVariable Integer id){
        Response response = this.fileTypeWritePlatformService.blockFileType(id);
        return response;
    }

    @PutMapping("/unblock/{id}")
    public Response unblockFileType(@PathVariable Integer id){
        Response response = this.fileTypeWritePlatformService.unblockFileType(id);
        return response;
    }
}
