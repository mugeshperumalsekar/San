package com.ponsun.san.xmlReadFile.ukSan.ukSanJsonImport.Service;

import java.io.IOException;
import java.util.List;

public interface CsvColumnService {

    List<String> getAllColumns() throws IOException;

    void addColumn(String newColumn) throws IOException;

    void removeColumn(String columnName) throws IOException;
}
