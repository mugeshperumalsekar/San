package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileService {
    private static final String directoryPath = "C:/Data/ProcessedData";
    private static final String jsonFileName = "ProcessedIndividualData.json";
    private static final String jsonFilePath = directoryPath + File.separator + jsonFileName;
    private static final String DIRECTORY_PATH = "C:/Data/ProcessedData";  // Directory path
    private static final String JSON_FILE_NAME = "ProcessedNonIndividualData.json";  // JSON File name
    private static final String JSON_FILE_PATH = DIRECTORY_PATH + File.separator + JSON_FILE_NAME; // Full file path

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HazelcastInstance hazelcastInstance;

    private static final int CHUNK_SIZE = 10000; // Define the chunk size (adjust as needed)

    // ✅ Write data to a JSON file
    public static void writeToJsonFile(List<ArabicSanData> batchData) {
        try {
            // Ensure directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directory if it doesn't exist
            }

            // Create ObjectMapper instance for JSON serialization
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Write list of ArabicSanData objects to JSON file
            objectMapper.writeValue(new File(jsonFilePath), batchData);

            // System.out.println("✅ Data successfully written to JSON: " + jsonFilePath);
        } catch (IOException ex) {
            // System.out.println("❌ ERROR writing to JSON file: " + ex.getMessage());
        }
    }

    // ✅ Read data from a JSON file
    public static List<ArabicSanData> readFromJsonFile() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();

            List<ArabicSanData> sanDataList = objectMapper.readValue(new File(jsonFilePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ArabicSanData.class));

            if (sanDataList == null || sanDataList.isEmpty()) {
                // System.out.println("❌ ERROR: No valid data found in JSON file.");
                return List.of(); // Return an empty list if no data is found
            }

            // System.out.println("✅ Data successfully read from JSON: " + jsonFilePath);
            return sanDataList;
        } catch (IOException ex) {
            // System.out.println("❌ ERROR reading JSON file: " + ex.getMessage());
            return List.of(); // Return an empty list if there was an error
        }
    }
    //////////////////////////////////////////Bulk Data reader////////////////////////////

//    private static final int CHUNK_SIZE = 100000; // Define the chunk size (adjust as needed)

    public static List<List<ArabicSanData>> readChunksFromJsonFile(Integer entityType) {
        try {
            String path = jsonFilePath;
            ObjectMapper objectMapper = new ObjectMapper();
            if (entityType == 1 || entityType == 3) {
                path = jsonFilePath;
            } else if (entityType == 2 || entityType == 4) {
                path = JSON_FILE_PATH;
            }

            // Read the entire list of ArabicSanData from the JSON file
            List<ArabicSanData> sanDataList = objectMapper.readValue(new File(path),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ArabicSanData.class));

            if (sanDataList == null || sanDataList.isEmpty()) {
                System.out.println("❌ ERROR: No valid data found in JSON file.");
                return List.of(); // Return an empty list if no data is found
            }

            // Remove duplicates and convert back to List
            List<ArabicSanData> distinctSanDataList = new ArrayList<>(new HashSet<>(sanDataList));
            // Chunk the data into smaller pieces
            List<List<ArabicSanData>> chunkedData = chunkData(distinctSanDataList);

            System.out.println("✅ Data successfully read from JSON: " + jsonFilePath);
            System.out.println("✅ Data successfully chunked into " + chunkedData.size() + " batches.");

            return chunkedData;
        } catch (IOException ex) {
            System.out.println("❌ ERROR reading JSON file: " + ex.getMessage());
            return List.of(); // Return an empty list if there was an error
        }
    }

//    public static List<List<ArabicSanData>> readChunksFromJsonFile() {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // Read the entire list of ArabicSanData from the JSON file
//            List<ArabicSanData> sanDataList = objectMapper.readValue(new File(jsonFilePath),
//                    objectMapper.getTypeFactory().constructCollectionType(List.class, ArabicSanData.class));
//
//            if (sanDataList == null || sanDataList.isEmpty()) {
//                // System.out.println("❌ ERROR: No valid data found in JSON file.");
//                return List.of(); // Return an empty list if no data is found
//            }
//            // Chunk the data into smaller pieces
//            List<List<ArabicSanData>> chunkedData = chunkData(sanDataList);
//
//            // System.out.println("✅ Data successfully read from JSON: " + jsonFilePath);
//            // System.out.println("✅ Data successfully chunked into " + chunkedData.size() + " batches.");
//
//            return chunkedData;
//        } catch (IOException ex) {
//            // System.out.println("❌ ERROR reading JSON file: " + ex.getMessage());
//            return List.of(); // Return an empty list if there was an error
//        }
//    }

    static List<List<ArabicSanData>> chunkData(List<ArabicSanData> fullList) {
        List<List<ArabicSanData>> chunkedData = new ArrayList<>();
        //Set<ArabicSanData> uniqueDataSet = new HashSet<>(arabicSanData);

        int totalSize = fullList.size();

        for (int i = 0; i < totalSize; i += CHUNK_SIZE) {
            int end = Math.min(i + CHUNK_SIZE, totalSize);
            chunkedData.add(new ArrayList<>(fullList.subList(i, end)));
        }

        return chunkedData;
    }
    ////////////////////////////////
    public static List<List<ArabicSanData>> readChunksFromJsonFileJsonFactory() {//JsonFactory
        List<List<ArabicSanData>> chunkedData = new ArrayList<>();
        try {
            // Create an ObjectMapper and JsonFactory
            ObjectMapper objectMapper = new ObjectMapper();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(new File(jsonFilePath));

            // List to hold the current chunk of data
            List<ArabicSanData> currentChunk = new ArrayList<>();

            // Start reading the JSON file
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    // Read each ArabicSanData object
                    ArabicSanData data = objectMapper.readValue(parser, ArabicSanData.class);
                    currentChunk.add(data);

                    // If we've reached the chunk size, add it to the result and clear it for the next chunk
                    if (currentChunk.size() == CHUNK_SIZE) {
                        chunkedData.add(new ArrayList<>(currentChunk));
                        currentChunk.clear();
                    }
                }
            }

            // If there are any leftover items in the last chunk, add them to the result
            if (!currentChunk.isEmpty()) {
                chunkedData.add(currentChunk);
            }

            // System.out.println("✅ Data successfully chunked into " + chunkedData.size() + " batches.");
        } catch (IOException ex) {
            // System.out.println("❌ ERROR reading JSON file: " + ex.getMessage());
            return List.of();  // Return an empty list if there was an error
        }
        return chunkedData;
    }
    ///////////////////////////////////////////////////////h c
    public static List<List<ArabicSanData>> readChunksFromJsonFileJsonFactoryCorewise(Integer entityType) {
        List<List<ArabicSanData>> chunkedData = new ArrayList<>();
        try {
//            JSON_FILE_PATH
            // Create an ObjectMapper and JsonFactory
            ObjectMapper objectMapper = new ObjectMapper();
            JsonFactory factory = new JsonFactory();

            JsonParser parser;
            if (entityType == 2) {
                parser = factory.createParser(new File(JSON_FILE_PATH));
            } else {
                parser = factory.createParser(new File(jsonFilePath));
            }
            // First pass: count the total number of records in the JSON file
//            int totalRecords = 0;
//            while (parser.nextToken() != JsonToken.END_ARRAY) {
//                if (parser.currentToken() == JsonToken.START_OBJECT) {
//                    totalRecords = totalRecords+1;
//                }
//            }

            // System.out.println(totalRecords);
            // Get the number of available processors (CPU cores)
            int availableProcessors = Runtime.getRuntime().availableProcessors();

            // Calculate chunk size: Divide the total records by available processors

            // System.out.println("chunkSize"+Runtime.getRuntime().availableProcessors());
            // Now, create a new parser and start processing the file
            parser.close();  // Close the previous parser and reopen to reset the cursor
            parser = factory.createParser(new File(jsonFilePath));

            // List to hold the current chunk of data
            List<ArabicSanData> currentChunk = new ArrayList<>();
            int totalRecords = 0;
            // Start reading the JSON file and chunking data
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    // Read each ArabicSanData object
                    ArabicSanData data = objectMapper.readValue(parser, ArabicSanData.class);
                    currentChunk.add(data);
                    totalRecords++;
                    // If we've reached the chunk size, add it to the result and clear it for the next chunk
//                    if (currentChunk.size() == chunkSize) {
//                        chunkedData.add(new ArrayList<>(currentChunk));
//                        currentChunk.clear();
//                    }
                }
            }
            int chunkSize = totalRecords/availableProcessors;//Math.max(1, totalRecords / availableProcessors);

            List<ArabicSanData> currentChunk11 = new ArrayList<>();

            for (ArabicSanData currentChunk1:currentChunk) {
                currentChunk11.add(currentChunk1);

                if (currentChunk11.size() == chunkSize) {
                        chunkedData.add(new ArrayList<>(currentChunk11));
                        currentChunk11.clear();
                    }
            }
            System.out.println("chunkSize:"+chunkSize);
            System.out.println(currentChunk.size());

            // If there are any leftover items in the last chunk, add them to the result
            if (!currentChunk.isEmpty()) {
                chunkedData.add(currentChunk);
            }

            // System.out.println("✅ Data successfully chunked into " + chunkedData.size() + " batches, with chunk size: " + chunkSize);
        } catch (IOException ex) {
            // System.out.println("❌ ERROR reading JSON file: " + ex.getMessage());
            return List.of();  // Return an empty list if there was an error
        }
        return chunkedData;
    }
//////////////////////////////////////////
    // ✅ Get Hazelcast Map
    private IMap<Integer, List<ArabicSanData>> getCacheMap() {
        return hazelcastInstance.getMap("ArabicSanDataMap");
    }


    // ✅ Read JSON File and Store in Hazelcast Efficiently
    public void readFromJsonFileInChunks() {
        File jsonFile = new File(JSON_FILE_PATH);
        if (!jsonFile.exists()) {
            // System.out.println("❌ ERROR: JSON file does not exist.");
            return;
        }

        // System.out.println("✅ File found. Reading JSON in chunks...");
        IMap<Integer, List<ArabicSanData>> cacheMap = getCacheMap();

        try (InputStream inputStream = new FileInputStream(jsonFile);
             JsonParser jsonParser = objectMapper.getFactory().createParser(inputStream)) {

            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Invalid JSON format! Expected an array.");
            }

            List<ArabicSanData> batchData = new LinkedList<>(); // ✅ Use LinkedList to reduce memory reallocations
            int batchSize = 100000;  // ✅ Increased batch size for fewer Hazelcast writes
            int batchCounter = 0;
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                ArabicSanData data = objectMapper.readValue(jsonParser, ArabicSanData.class);
                batchData.add(data);

                if (batchData.size() >= batchSize) {
                    List<ArabicSanData> batchCopy = new ArrayList<>(batchData);
                    futures.add(storeDataInHazelcastAsync(cacheMap, batchCounter++, batchCopy)); // ✅ Async Hazelcast storage
                    batchData.clear();
                }
            }

            if (!batchData.isEmpty()) {
                futures.add(storeDataInHazelcastAsync(cacheMap, batchCounter, new ArrayList<>(batchData)));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join(); // ✅ Wait for all async tasks
            // System.out.println("✅ All data stored in Hazelcast.");
        } catch (IOException ex) {
            // System.out.println("❌ ERROR reading JSON file: " + ex.getMessage());
        }
    }

    // ✅ Store Data in Hazelcast in Chunks
    private CompletableFuture<Void> storeDataInHazelcastAsync(IMap<Integer, List<ArabicSanData>> cacheMap, int batchIndex, List<ArabicSanData> dataList) {
        return CompletableFuture.runAsync(() -> {
            cacheMap.put(batchIndex, dataList);  // ✅ Synchronous put (faster than putAsync for bulk data)
            // System.out.println("✅ Stored " + dataList.size() + " records in Hazelcast under key: Batch-" + batchIndex);
        });
    }

}

