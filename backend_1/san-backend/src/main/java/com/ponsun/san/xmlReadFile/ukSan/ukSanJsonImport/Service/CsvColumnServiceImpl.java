package com.ponsun.san.xmlReadFile.ukSan.ukSanJsonImport.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvColumnServiceImpl implements CsvColumnService{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path filePath = Paths.get("src/main/resources/logconfig/csv_columns.json");

    @Override
    public List<String> getAllColumns() throws IOException {
        CsvColumns csvColumns = objectMapper.readValue(filePath.toFile(), CsvColumns.class);
        return csvColumns.getCsv_columns();
    }

    @Override
    public void addColumn(String newColumn) throws IOException {
        CsvColumns csvColumns = objectMapper.readValue(filePath.toFile(), CsvColumns.class);
        List<String> current = csvColumns.getCsv_columns();

        if (!current.contains(newColumn)) {
            current.add(newColumn);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), csvColumns);
        } else {
            throw new IllegalArgumentException("Column already exists");
        }
    }

    @Override
    public void removeColumn(String columnName) throws IOException {
        CsvColumns csvColumns = objectMapper.readValue(filePath.toFile(), CsvColumns.class);
        List<String> current = csvColumns.getCsv_columns();

        if (current.remove(columnName)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), csvColumns);
        } else {
            throw new IllegalArgumentException("Column not found");
        }
    }
}

