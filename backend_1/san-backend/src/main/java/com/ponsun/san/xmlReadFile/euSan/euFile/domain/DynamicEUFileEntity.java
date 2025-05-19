package com.ponsun.san.xmlReadFile.euSan.euFile.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EU_csv_file")
public class DynamicEUFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Entity_LogicalId;

    private String fileGenerationDate;

}