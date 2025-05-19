package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileWriterService {

    private static final String DIRECTORY_PATH = "D:/Data/FileSave";
    private static final String FILE_PATH = DIRECTORY_PATH + "/FileSave.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveData(Integer entityType, String name1, String name2,
                         String id1, String id2, String country1,
                         String country2, String dob1, String dob2) {
        try {
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Map<String, Object> jsonData = new LinkedHashMap<>();
            jsonData.put("entityType", entityType);
            jsonData.put("name1", name1);
            jsonData.put("name2", name2);
            jsonData.put("id1", id1);
            jsonData.put("id2", id2);
            jsonData.put("country1", country1);
            jsonData.put("country2", country2);
            jsonData.put("dob1", dob1);
            jsonData.put("dob2", dob2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String formattedTimestamp = LocalDateTime.now().format(formatter);
            jsonData.put("timestamp", formattedTimestamp);

            String jsonString = objectMapper.writeValueAsString(jsonData);

            try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
                writer.write(jsonString + System.lineSeparator());
            }

        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
