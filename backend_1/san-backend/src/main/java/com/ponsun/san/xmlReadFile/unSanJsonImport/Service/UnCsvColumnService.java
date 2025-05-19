package com.ponsun.san.xmlReadFile.unSanJsonImport.Service;

import java.io.IOException;
import java.util.List;

public interface UnCsvColumnService {

    List<String> getAllColumns() throws IOException;

    List<String> addColumnsFromLine(String columnLine) throws IOException;

    List<String> addColumn(String newColumn) throws IOException;

    void removeColumn(String columnName) throws IOException;
}
