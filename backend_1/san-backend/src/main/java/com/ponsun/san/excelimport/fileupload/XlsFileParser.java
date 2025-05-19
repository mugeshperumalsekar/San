package com.ponsun.san.excelimport.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class XlsFileParser implements FileParser {
    @Override
    public List<Map<String, Object>> parseExcelDataFromStream(MultipartFile file) {
        List<Map<String, Object>> results = new ArrayList<>();

        String[] expectedColumns = {"name", "score", "type", "country"};

        try (InputStream fis = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                for (int i = 0; i < expectedColumns.length; i++) {
                    Cell cell = headerRow.getCell(i);
                    if (cell == null || !cell.getStringCellValue().equals(expectedColumns[i])) {
                        throw new IllegalArgumentException("Column mismatch: Expected " + expectedColumns[i] + " at index " + i);
                    }
                }
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, Object> resultData = new HashMap<>();
                Iterator<Cell> cellIterator = row.cellIterator();
                boolean validRow = true;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getColumnIndex()) {
                        case 0:
                            resultData.put("name", getCellValue(cell));
                            break;
                        case 1:
                            resultData.put("score", getCellValue(cell));
                            break;
                        case 2:
                            resultData.put("type", getCellValue(cell));
                            break;
                        case 3:
                            resultData.put("country", getCellValue(cell));
                            break;
                        default:
                            validRow = false;
                            log.warn("Unexpected column at index " + cell.getColumnIndex());
                            break;
                    }
                }
                if (validRow) {
                    results.add(resultData);
                } else {
                    log.error("Row " + row.getRowNum() + " contains invalid data.");
                }
            }
        } catch (IOException e) {
            log.error("Error reading Excel file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.error("Error parsing Excel file: " + e.getMessage(), e);
            throw e;
        }
        return results;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return "";
        }
    }
}
