package com.ponsun.san.xmlReadFile.ofacSan.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "OFAC_csv_file")
public class DynamicOFACFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Entity_LogicalId;

    private String fileGenerationDate;
}
