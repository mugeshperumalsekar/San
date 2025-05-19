package com.ponsun.san.xmlReadFile.unscfile.unexpectedcsv;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unexpected_csv")
public class UnExpectedCsv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Entity_LogicalId;

    private String fileGenerationDate;

}