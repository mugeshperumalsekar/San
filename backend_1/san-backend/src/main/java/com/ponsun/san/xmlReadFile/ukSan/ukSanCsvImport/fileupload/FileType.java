package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.fileupload;

public enum FileType {
    XLS("xls"), CSV("csv");

    public String fileType;

    FileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return this.fileType;
    }
}
