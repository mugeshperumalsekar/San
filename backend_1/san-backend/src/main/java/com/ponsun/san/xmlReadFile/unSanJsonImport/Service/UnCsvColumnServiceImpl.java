package com.ponsun.san.xmlReadFile.unSanJsonImport.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnCsvColumnServiceImpl implements UnCsvColumnService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path filePath = Paths.get("src/main/resources/logconfig/un_csv_columns.json");

    @Override
    public List<String> getAllColumns() throws IOException {
        UnCsvColumns unCsvColumns = objectMapper.readValue(filePath.toFile(), UnCsvColumns.class);
//        System.out.println(unCsvColumns.getUn_Csv_columns().size());
        return unCsvColumns.getUn_Csv_columns();
    }

    @Override
    public List<String> addColumnsFromLine(String columnLine) throws IOException {
        UnCsvColumns unCsvColumns = objectMapper.readValue(filePath.toFile(), UnCsvColumns.class);
        List<String> current = unCsvColumns.getUnCsvColumns();

        // Split the string by comma, trim each value
        List<String> newColumns = Arrays.stream(columnLine.split(","))
                .map(String::trim)
                .filter(c -> !c.isEmpty())
                .collect(Collectors.toList());

        boolean updated = false;

        for (String newColumn : newColumns) {
            if (!current.contains(newColumn)) {
                current.add(newColumn);
                updated = true;
            }
        }

        if (updated) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), unCsvColumns);
        }

        return current;
    }

    @Override
    public List<String> addColumn(String newColumn) throws IOException {
        UnCsvColumns unCsvColumns = objectMapper.readValue(filePath.toFile(), UnCsvColumns.class);
        List<String> current = unCsvColumns.getUn_Csv_columns();

        if (!current.contains(newColumn)) {
            current.add(newColumn);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), unCsvColumns);
        } else {
            throw new IllegalArgumentException("Column already exists");
        }
        return current;
    }

    @Override
    public void removeColumn(String columnName) throws IOException {
        UnCsvColumns unCsvColumns = objectMapper.readValue(filePath.toFile(), UnCsvColumns.class);
        List<String> current = unCsvColumns.getUn_Csv_columns();

        if (current.remove(columnName)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), unCsvColumns);
        } else {
            throw new IllegalArgumentException("Column not found");
        }
    }
}

