package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;

import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.time.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.ponsun.san.algorithm.service.BatchProcessorTesting.*;
import static com.ponsun.san.oneSidePara.oneSideMultiPara.service.FileService.chunkData;

@Service
@Slf4j
@RequiredArgsConstructor
public class UiReciveSingleRecord {
    private final BatchProcessor batchProcessor;
    private final OnBoardBatchProcessor onBoardbatchProcessor;
    private final FileService fileService;
    private final HazelcastInstance hazelcastInstance; // 🔥 FIX: Inject HazelcastInstance
    private final FileWriterService fileWriterService;
    private final int corePoolSize = 8;
    private final int maximumPoolSize = 16;
    long keepAliveTime = 60L;
    TimeUnit unit = TimeUnit.SECONDS;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
            keepAliveTime, unit, workQueue);

    @SneakyThrows
    public List<UiReciveSingleRecordDto> uicalculateScoreSinglePara(MultiParaSearchData multiParaSearchData) {
        List<UiReciveSingleRecordDto> uirecordDTOS = new CopyOnWriteArrayList<>();

        System.out.println("Start Time: " + LocalTime.now());
        List<List<ArabicSanData>> arabicSanchunkedData = fileService.readChunksFromJsonFile(multiParaSearchData.getEntityType());

        String filter1              = Preprocess.datafilter_Init(multiParaSearchData.getName());
        List<String> token1         = Functions.tokenizeString_Array(filter1);

        List<Map<String, List<String>>> mapList1 = new ArrayList<>();
        if(multiParaSearchData.getEntityType()==1)  {
            LinkedHashSet<String> d1 = Functions.dateTokenize(multiParaSearchData.getDob());
            for (String date : d1) {
                mapList1.add(DOBMatching.parseInput(date));
            }
        }
        // Call the refactored reusable method to process chunks based on entity type
        processChunks(multiParaSearchData, arabicSanchunkedData, filter1, token1, mapList1, uirecordDTOS);

        System.out.println("Total records Processed - End Time: " + LocalTime.now());
        return uirecordDTOS;
    }

    @SneakyThrows
    public List<UiReciveSingleRecordDto> calculateSanListScore(MultiParaSearchData multiParaSearchData,List<ArabicSanData> sanList) {
        List<UiReciveSingleRecordDto> uirecordDTOS = new CopyOnWriteArrayList<>();

        System.out.println("Start Time: " + LocalTime.now());

        List<List<ArabicSanData>> arabicSanchunkedData = chunkData(sanList);

        String filter1              = Preprocess.datafilter_Init(multiParaSearchData.getName());
        List<String> token1         = Functions.tokenizeString_Array(filter1);

        List<Map<String, List<String>>> mapList1 = new ArrayList<>();
        if(multiParaSearchData.getEntityType()==1)  {
            LinkedHashSet<String> d1 = Functions.dateTokenize(multiParaSearchData.getDob());
            for (String date : d1) {
                mapList1.add(DOBMatching.parseInput(date));
            }
        }
        // Call the refactored reusable method to process chunks based on entity type
        processChunks(multiParaSearchData, arabicSanchunkedData, filter1, token1, mapList1, uirecordDTOS);

        System.out.println("Total records Processed - End Time: " + LocalTime.now());
        return uirecordDTOS;
    }
//    private void processChunks(MultiParaSearchData multiParaSearchData, List<List<ArabicSanData>> arabicSanchunkedData, String filter1,
//                               List<String> token1, List<Map<String, List<String>>> mapList1,
//                               List<UiReciveSingleRecordDto> uirecordDTOS) {
//
//        List<String> temp   = null;
//        int entityType      = multiParaSearchData.getEntityType();
//        LinkedHashSet<String> suffixes = null;
//
//        String  name1_withoutsuf        = "";
//        List<String> token1_withoutsuf  = null;
//        if(entityType == 2){
//            suffixes            = SuffixProcess.ReadSuff();
//            name1_withoutsuf    = SuffixProcess.RemoveSuff(suffixes,filter1);
//            token1_withoutsuf   = Functions.tokenizeString_Array(name1_withoutsuf);
//        }
//        if(entityType == 3) {
//            name1_withoutsuf    = SuffixProcess.RemoveSuff_ind(filter1);
//            token1_withoutsuf   = Functions.tokenizeString_Array(name1_withoutsuf);
//        }
//        LinkedHashSet<String> finalSuffixes = suffixes;
//        String finalName1_withoutsuf = name1_withoutsuf;
//        List<String> finalToken1_withoutsuf = token1_withoutsuf;
//
//
//        List<CompletableFuture<Void>> futures = arabicSanchunkedData.stream()
//                .map(chunk -> CompletableFuture.runAsync(() -> {
//                    List<UiReciveSingleRecordDto> chunkResult = null;
//                    switch (entityType) {
//                        case 1://
//                            chunkResult = processChunksInParallelWithFilter(chunk, multiParaSearchData, filter1, token1, mapList1,"",temp);
//                            break;
//                        case 2:
//                            chunkResult = processEntityChunksInParallelWithFilter(finalSuffixes, finalName1_withoutsuf, finalToken1_withoutsuf,chunk, multiParaSearchData,filter1, token1);
//                            break;
//                        case 3:
//                            chunkResult = processChunksInParallelWithFilter(chunk,multiParaSearchData, filter1, token1, mapList1, finalName1_withoutsuf, finalToken1_withoutsuf);
//                            break;
//                        case 4:
//                            chunkResult = processChunksInParallelWithFilter(chunk, multiParaSearchData,filter1, token1,mapList1,"",temp);
//                            break;
//                        default:
//                            throw new IllegalArgumentException("Unknown entity type: " + entityType);
//                    }
//
//                    synchronized (uirecordDTOS) {
//                        uirecordDTOS.addAll(chunkResult); // Thread-safe due to synchronized block
//                    }
//                }, executorService))
//                .collect(Collectors.toList());
//
//        // Wait for all futures to complete
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//    }

    private void processChunks(MultiParaSearchData multiParaSearchData, List<List<ArabicSanData>> arabicSanchunkedData, String filter1,
                               List<String> token1, List<Map<String, List<String>>> mapList1,
                               List<UiReciveSingleRecordDto> uirecordDTOS) {

        List<String> temp = null;

        LinkedHashSet<String> suffixes = SuffixProcess.ReadSuff();
        String name1_withoutsuf_2 = SuffixProcess.RemoveSuff(suffixes, filter1);
        List<String> token1_withoutsuf_2 = Functions.tokenizeString_Array(name1_withoutsuf_2);

        String name1_withoutsuf_3 = SuffixProcess.RemoveSuff_ind(filter1);
        List<String> token1_withoutsuf_3 = Functions.tokenizeString_Array(name1_withoutsuf_3);

        List<CompletableFuture<Void>> futures = arabicSanchunkedData.stream()
                .map(chunk -> CompletableFuture.runAsync(() -> {
                    // For entityType 1
                    List<UiReciveSingleRecordDto> chunkResult1 = processChunksInParallelWithFilter(chunk, multiParaSearchData, filter1, token1, mapList1, "", temp);
                    // For entityType 2
                    List<UiReciveSingleRecordDto> chunkResult2 = processEntityChunksInParallelWithFilter(suffixes, name1_withoutsuf_2, token1_withoutsuf_2, chunk, multiParaSearchData, filter1, token1);
                    // For entityType 3
                    List<UiReciveSingleRecordDto> chunkResult3 = processChunksInParallelWithFilter(chunk, multiParaSearchData, filter1, token1, mapList1, name1_withoutsuf_3, token1_withoutsuf_3);
                    // For entityType 4
                    List<UiReciveSingleRecordDto> chunkResult4 = processChunksInParallelWithFilter(chunk, multiParaSearchData, filter1, token1, mapList1, "", temp);

                    synchronized (uirecordDTOS) {
                        uirecordDTOS.addAll(chunkResult1);
                        uirecordDTOS.addAll(chunkResult2);
                        uirecordDTOS.addAll(chunkResult3);
                        uirecordDTOS.addAll(chunkResult4);
                    }
                }, executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    /////////////////////////////////////////////////////////AS st
    @SneakyThrows
    public List<UiReciveSingleRecordDto> uicalculateScoreSinglePara1(MultiParaSearchData multiParaSearchData) {
        // System.out.println("✅ Checking Hazelcast Cache...");

        AtomicInteger totalRecordsRead = new AtomicInteger(0);
        LocalTime startTime = LocalTime.now(); // Capture start time
        IMap<Integer, List<ArabicSanData>> cacheMap = hazelcastInstance.getMap("ArabicSanDataMap");

        // ✅ Step 1: Load data if Hazelcast is empty
        if (cacheMap.isEmpty()) {
            // System.out.println("❌ Hazelcast is empty. Reading data from JSON...");
            fileService.readFromJsonFileInChunks();
        }

        // ✅ Step 2: Retrieve data in parallel
        List<UiReciveSingleRecordDto> uirecordDTOS = Collections.synchronizedList(new ArrayList<>());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        Set<Integer> keys = cacheMap.keySet(); // ✅ Fetch all keys first

        keys.parallelStream().forEach(batchIndex -> {
            futures.add(CompletableFuture.runAsync(() -> {
                List<ArabicSanData> batchData = cacheMap.get(batchIndex);

                if (batchData == null || batchData.isEmpty())
                    return;

//                totalRecordsRead.addAndGet(batchData.size());
//                // System.out.println("✅ Processing batch size: " + batchData.size() + " | Total records read: " + totalRecordsRead.get());

                int batchSize = 50000;
                for (int i = 0; i < batchData.size(); i += batchSize) {
                    List<ArabicSanData> currentBatch = batchData.subList(i, Math.min(i + batchSize, batchData.size()));
                    if (!currentBatch.isEmpty()) {
                        batchProcessor.processBatch1(currentBatch, multiParaSearchData, uirecordDTOS);
                    }
                }
            }));
        });

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join(); // ✅ Wait for parallel processing

        LocalTime endTime = LocalTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.toSeconds();

        // System.out.println("✅✅✅ ALL DATA SUCCESSFULLY READ! 🚀");
        // System.out.println("🚀 FINAL TOTAL RECORDS READ: " + totalRecordsRead.get() + " | End Time: " + endTime + " | Total Time: " + seconds + " sec");

        return uirecordDTOS;
    }


    /////////////////////////////////////////////////////////////////AS end


    public double[] calculateScorestestingService(Integer entityType, String firstName, String secondName, String id1, String id2, String country1, String country2, String dob1, String dob2) throws Exception {

        fileWriterService.saveData(entityType, firstName, secondName, id1, id2, country1, country2, dob1, dob2);

        double[] scores = new double[18];
        if (entityType == 1)
            scores = batchProcessor.calculateScoresAtSingleOut(firstName, secondName, id1, id2, country1, country2, dob1, dob2);
        else if (entityType == 2) {
            scores = CoperateBatchProcessor.processOnBoardBatch(firstName, secondName, id1, id2, country1, country2, dob1, dob2);
        } else if (entityType == 3) {
            scores = IndividualRemittance.IndividualRemittanceMatches(firstName, secondName, id1, id2, country1, country2);
        } else if (entityType == 4) {
            scores = EntityRemittance.EntityRemittanceMatches(firstName, secondName, id1, id2, country1, country2);
        }
        return scores;
    }

    @SneakyThrows
    public List<UiReciveSingleRecordDto> calculateOnBoardScore(MultiParaSearchData multiParaSearchData) {

        List<UiReciveSingleRecordDto> uirecordDTOS = new ArrayList<>();
        List<ArabicSanData> arabicSanData = new ArrayList<>();

        // System.out.println("Total records in arabicSanData: " + arabicSanData.size()+" : Start Time"+ LocalTime.now());

        //batchProcessor.processOnBoardBatch(arabicSanData, multiParaSearchData, uirecordDTOS);

        // System.out.println("Total records Process : End Time"+LocalTime.now());
        // System.out.println(" records  :"+uirecordDTOS);

        // System.out.println("Total records processed: " + uirecordDTOS);
        return uirecordDTOS;
    }

    // Graceful shutdown of executor service (usually done when the application shuts down)
    @PreDestroy
    public void shutdownExecutorService() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    //    //////////////////////////testing process//////////////////
    private static final String directoryPath = "D:/Data/ProcessedData";
    private static final String jsonFileName = "ProcessedData1L data.json";
    private static final String jsonFilePath = directoryPath + File.separator + jsonFileName;

    @SneakyThrows
    public List<UiReciveSingleRecordDto> uicalculateScoreSingleParaTest(MultiParaSearchData multiParaSearchData) {
        // Start processing the JSON file in chunks
        // System.out.println("Start Time: " + LocalTime.now());

        // Process each record as it's read from the JSON file
        List<UiReciveSingleRecordDto> a = processJsonFileDirectly(multiParaSearchData);
        // System.out.println("Data:"+a.size());
        // System.out.println("Total records Processed: End Time " + LocalTime.now());
        return a;
    }

    public List<UiReciveSingleRecordDto> processJsonFileDirectly(MultiParaSearchData multiParaSearchData) {
        List<UiReciveSingleRecordDto> uirecordDTOS = new CopyOnWriteArrayList<>();
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<CompletableFuture<List<UiReciveSingleRecordDto>>> futures = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ConcurrentLinkedQueue<UiReciveSingleRecordDto> resultQueue = new ConcurrentLinkedQueue<>();

        try {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(new File(jsonFilePath));
            var ref = new Object() {
                List<ArabicSanData> currentChunk = new ArrayList<>();
            };
            final int CHUNK_SIZE = 10000;

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    // Deserialize a single object (ArabicSanData) as it is read
                    ArabicSanData data = objectMapper.readValue(parser, ArabicSanData.class);

                    ref.currentChunk.add(data);

                    if (ref.currentChunk.size() >= CHUNK_SIZE) {
                        // System.out.println(ref.currentChunk.size() + " : " + LocalTime.now());

                        // Use CompletableFuture to process each chunk asynchronously in parallel
                        CompletableFuture<List<UiReciveSingleRecordDto>> future = CompletableFuture.supplyAsync(() -> {
                            return processChunksInParallel(ref.currentChunk, multiParaSearchData);
                        }, executorService);  // Using the executorService for parallel processing

                        // Collect the futures to wait for them later
                        futures.add(future);
                        // Collect records and process them in batches
//                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                            List<UiReciveSingleRecordDto> chunkResult = processChunksInParallel(ref.currentChunk, multiParaSearchData);
//                            synchronized (resultQueue) {
//                                resultQueue.addAll(chunkResult);  // Ensure thread safety when updating the list
//                            }
//                            // Instead of directly modifying the list, add the result to the thread-safe queue
////                            resultQueue.addAll(chunkResult);  // ConcurrentLinkedQueue is thread-safe
//                        }, executorService);
//
//                        futures.add(future);
                        ref.currentChunk = new ArrayList<>();
                    }
                }
            }

            // Handle the remaining chunk if it exists
//            if (!ref.currentChunk.isEmpty()) {
//                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                    List<UiReciveSingleRecordDto> chunkResult = processChunksInParallel(ref.currentChunk, multiParaSearchData);
//                    resultQueue.addAll(chunkResult);
//                }, executorService);
//                futures.add(future);
//            }

            // Wait for all futures to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            // Now collect all results from the futures
            for (CompletableFuture<List<UiReciveSingleRecordDto>> future : futures) {
                // System.out.println("A:"+future.join());
                uirecordDTOS.addAll(future.join());  // Get the result of each future and add it to the final list
            }
            parser.close();  // Close the parser after reading

            // Collect results from the thread-safe queue into a list
//            uirecordDTOS.addAll(resultQueue);

            return new ArrayList<>(uirecordDTOS);

        } catch (IOException e) {
            // System.out.println("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }
    /////////////////////////////////////////////////////////////////////


/////////////////////
//public List<UiReciveSingleRecordDto> uicalculateScoreSinglePara(MultiParaSearchData multiParaSearchData) {
//    List<CompletableFuture<Void>> futures = new ArrayList<>();
//
//    // Use ConcurrentLinkedQueue to safely add results from different threads
//    Queue<UiReciveSingleRecordDto> uirecordDTOS = new ConcurrentLinkedQueue<>();
//
//    // Executor Service
////    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//    // Use JsonFactory and JsonParser to stream the JSON data and process each item directly
//    try (JsonParser parser = new JsonFactory().createParser(new File(jsonFilePath))) {
//
//        var ref = new Object() {
//            List<ArabicSanData> currentChunk = new ArrayList<>();
//        };
//        final int CHUNK_SIZE = 1000000; // Adjust chunk size as per your requirement
//
//        // Loop through the JSON array and process each object
//        while (parser.nextToken() != JsonToken.END_ARRAY) {
//
//            if (parser.currentToken() == JsonToken.START_OBJECT) {
//                // Deserialize a single object (ArabicSanData) as it is read
//                ArabicSanData data = new ObjectMapper().readValue(parser, ArabicSanData.class);
//                ref.currentChunk.add(data);
//
//                if (ref.currentChunk.size() >= CHUNK_SIZE) {
//                    // System.out.println("Processing chunk of size: " + ref.currentChunk.size() + " at " + LocalTime.now());
//
//                    // Process the chunk asynchronously
//                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                        List<UiReciveSingleRecordDto> chunkResult = processChunksInParallel(ref.currentChunk, multiParaSearchData);
//                        uirecordDTOS.addAll(chunkResult); // Use ConcurrentQueue to add results safely
//                    }, executorService);
//
//                    futures.add(future);
//                    ref.currentChunk = new ArrayList<>(); // Clear the current chunk for the next batch
//                }
////                else
////                {
////                    // Handle any remaining records in the last chunk
////                    if (!ref.currentChunk.isEmpty()) {
////                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
////                            List<UiReciveSingleRecordDto> chunkResult = processChunksInParallel(ref.currentChunk, multiParaSearchData);
////                            uirecordDTOS.addAll(chunkResult); // Use ConcurrentQueue to add results safely
////                        }, executorService);
////                        futures.add(future);
////                    }
////                }
//            }
//
//        }
//        // Wait for all async tasks to complete
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//
//
//    } catch (IOException e) {
//        // System.out.println("Error reading JSON file: " + e.getMessage());
//    } finally {
//        executorService.shutdown(); // Ensure to shutdown the executor service
//    }
//
//    // Convert ConcurrentLinkedQueue to List
//    return new ArrayList<>(uirecordDTOS);
//}
///////////////////
}




