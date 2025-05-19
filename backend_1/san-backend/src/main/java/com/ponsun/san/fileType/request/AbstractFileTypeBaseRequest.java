package com.ponsun.san.fileType.request;

import lombok.Data;

@Data
public class AbstractFileTypeBaseRequest {
    private String name;
    private Boolean valid;
    private Integer uid;
    private Integer euid;
}
