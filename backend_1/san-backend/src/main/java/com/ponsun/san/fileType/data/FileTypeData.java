package com.ponsun.san.fileType.data;

import lombok.Data;

@Data
public class FileTypeData {
    private  Integer id;
    private String name;
    private Boolean valid;
    private Integer uid;
    private Integer euid;

    public FileTypeData(final Integer id,final String name,final Boolean valid,final Integer uid,final Integer euid){
        this.id = id;
        this.name = name;
        this.valid = valid;
        this.uid = uid;
        this.euid = euid;
    }
    public static  FileTypeData newInstance(final Integer id,final String name,final Boolean valid,final Integer uid,final Integer euid){
        return new FileTypeData(id,name,valid,uid,euid);
    }
}
