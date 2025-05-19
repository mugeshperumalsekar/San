package com.ponsun.san.xmlReadFile.unscfile.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

@Slf4j
public class XlsFileParser implements FileParser {

    private final String fileName;
    private final String directoryPath;

    public XlsFileParser(String directoryPath, String fileName) {
        this.directoryPath = directoryPath;
        this.fileName = fileName;
    }

    @Override
    public List<Map<String, Object>> parseExcelData() {
        log.debug("Start of parseExcelData()");
        List<Map<String, Object>> results = new ArrayList<>();
        File xlsFile;
        FileInputStream fis = null;
        try {
            xlsFile = new File(this.directoryPath + File.separator + fileName);
            fis = new FileInputStream(xlsFile);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator();
            Row headerRow = mySheet.getRow(0);
            Map<Integer, String> columnNames = new HashMap<>();
            for (Cell cell : headerRow) {
                columnNames.put(cell.getColumnIndex(), getCellValue(cell).toString());
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                final Map<String, Object> resultData = new HashMap<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int colIndex = cell.getColumnIndex();
                    String key = columnNames.get(colIndex);

                    if (key != null) {
                        Object value = getCellValue(cell);
                        resultData.put(key, value);
                    }
                }
                log.debug("END of parseExcelData()");
                results.add(resultData);
            }
        } catch (Exception e) {
            log.error("Error while parsing file data: ", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                log.error("Error while closing file input stream: ", e);
            }
        }
        return results;
    }

    public List<String> getAllHeaders() {
        List<String> headers = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String filePath = this.directoryPath + File.separator + fileName;

        log.debug("Starting to read headers from CSV file: {}", filePath);

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            log.debug("Successfully opened the CSV file: {}", filePath);

            String headerLine = bufferedReader.readLine();
            if (headerLine != null) {
                log.debug("Header line read: {}", headerLine);
                headers = Arrays.asList(headerLine.split(","));
                log.debug("Extracted headers: {}", headers);
            } else {
                log.warn("No headers found in the file: {}", filePath);
            }

        } catch (IOException e) {
            log.error("Error while reading headers from file: {}", filePath, e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    log.debug("BufferedReader closed successfully.");
                }
                if (fileReader != null) {
                    fileReader.close();
                    log.debug("FileReader closed successfully.");
                }
            } catch (IOException e) {
                log.error("Error while closing resources", e);
            }
        }

        log.debug("Completed reading headers. Total headers found: {}", headers.size());
        return headers;
    }


    public List<Map<String, Object>> parseCsvData() {
        List<Map<String, Object>> results = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String filePath = this.directoryPath + File.separator + fileName;

        log.debug("Starting CSV parsing from file: {}", filePath);

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            log.debug("Successfully opened the CSV file: {}", filePath);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(bufferedReader);
            log.debug("CSV file parsed successfully. Processing records...");

            int recordCount = 0;
            for (CSVRecord record : records) {
                Map<String, Object> resultData = new HashMap<>();
                log.debug("Processing record #{}", ++recordCount);

                for (String header : record.toMap().keySet()) {
                    String value = record.get(header);
                    if (value != null && !value.isEmpty()) {
                        resultData.put(header, value);
                    } else {
                        resultData.put(header, null);
                    }
                }

                log.debug("Record #{}: DATAID = {}", recordCount, resultData.get("DATAID"));
                results.add(resultData);
            }

            log.debug("CSV parsing completed successfully. Total records processed: {}", recordCount);

        } catch (IOException e) {
            log.error("Error while reading CSV file: {}", filePath, e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    log.debug("BufferedReader closed successfully.");
                }
                if (fileReader != null) {
                    fileReader.close();
                    log.debug("FileReader closed successfully.");
                }
            } catch (IOException e) {
                log.error("Error while closing resources", e);
            }
        }

        return results;
    }
    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (int) numericValue) {
                        return (int) numericValue;
                    } else {
                        return numericValue;
                    }
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}