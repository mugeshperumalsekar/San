package com.ponsun.san.xmlReadFile.ukSan.ukSanCsvImport.fileupload;

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

        try {
            fileReader = new FileReader(this.directoryPath + File.separator + fileName);
            bufferedReader = new BufferedReader(fileReader);

            // Read the first line (assuming headers are on the first line)
            String headerLine = bufferedReader.readLine();
            if (headerLine != null) {
                // Split the header line by delimiter (e.g., comma for CSV)
                headers = Arrays.asList(headerLine.split(","));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return headers;
    }




    public List<Map<String, Object>> parseCsvData() {
        List<Map<String, Object>> results = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(this.directoryPath + File.separator + fileName);
            bufferedReader = new BufferedReader(fileReader);

            // Initialize CSVParser to read the file using Apache Commons CSV
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(bufferedReader);

            for (CSVRecord record : records) {
                Map<String, Object> resultData = new HashMap<>();

                // Iterate over all headers and columns
                for (String header : record.toMap().keySet()) {
                    String value = record.get(header); // Get value from record
                    if (value != null && !value.isEmpty()) {
                        resultData.put(header, value); // Store value if present
                    } else {
                        resultData.put(header, null);  // Otherwise store null
                    }
                }
//                System.out.println("UK Statement of Reasons: " + resultData.get("UK Statement of Reasons"));

                // Add the result data to the list
                results.add(resultData);

                // You can process and store the data incrementally, instead of storing all of it in memory at once.
                // For example, if you need to persist the data to a database, do it here.
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
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