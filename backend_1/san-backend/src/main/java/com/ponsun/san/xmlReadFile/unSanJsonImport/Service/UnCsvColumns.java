package com.ponsun.san.xmlReadFile.unSanJsonImport.Service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UnCsvColumns {
    private List<String> un_csv_columns;

    @JsonProperty("un_csv_columns")
    private List<String> unCsvColumns;

    public List<String> getUn_Csv_columns() {
        return unCsvColumns;
    }

    public List<String> getUnCsvColumns() {
        return unCsvColumns;
    }

    public void setUnCsvColumns(List<String> unCsvColumns) {
        this.unCsvColumns = unCsvColumns;
    }
//    public void setCsv_columns(List<String> un_csv_columns) {
//        this.un_csv_columns = un_csv_columns;
//    }
}
