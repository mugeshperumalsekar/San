package com.ponsun.san.oneSidePara.oneSideMultiPara.service;


import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class BatchProcessorWithMultiThread {
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 32; // Dynamic thread allocation
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void processBatch1(
            List<ArabicSanData> batchData,
            MultiParaSearchData multiParaSearchData,
            List<UiReciveSingleRecordDto> uirecordDTOS) {

        AtomicInteger processedCount = new AtomicInteger(0);

        // ✅ Dynamically set chunk size
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int chunkSize = Math.max(1, batchData.size() / (availableProcessors * 2));

        // ✅ Split into chunks
        List<List<ArabicSanData>> chunks = chunkListParallel(batchData, chunkSize);
        // System.out.println("[DEBUG] Starting batch processing with " + chunks.size() + " chunks.");

        // ✅ Use thread-safe list to store results
        List<UiReciveSingleRecordDto> threadSafeResults = Collections.synchronizedList(new ArrayList<>());

        // ✅ Submit tasks to `ExecutorService`
        List<Future<?>> futures = chunks.stream()
                .map(chunk -> executorService.submit(() -> {
                    // Process the chunk with ChunkProcessor and invoke its process method
                    new ChunkProcessor(chunk, multiParaSearchData, threadSafeResults).process();
                    return null;  // Return null to ensure Future<Void> is returned
                }))
                .collect(Collectors.toList());

// Optionally, wait for all tasks to complete (this blocks until all tasks finish):
        futures.stream().forEach(future -> {
            try {
                future.get();  // Wait for each task to complete
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
        // ✅ Create & Start Threads
//        List<Thread> threads = new ArrayList<>();
//        for (List<ArabicSanData> chunk : chunks) {
//            Thread thread = new Thread(new ChunkProcessor(chunk, multiParaSearchData, threadSafeResults));
//            threads.add(thread);
//            thread.start();
//        }
//
//        // ✅ Wait for all threads to finish
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                System.err.println("[ERROR] Thread interrupted: " + e.getMessage());
//            }
//        }

        // ✅ Apply filtering before adding to the output list
        uirecordDTOS.addAll(threadSafeResults.stream()
                .filter(Objects::nonNull)
                .filter(dto -> Arrays.stream(dto.getOnsideMultiPara()).max().orElse(Double.NaN) >= multiParaSearchData.getScore())
                .collect(Collectors.toList()));

        // System.out.println("[DEBUG] Final processed records: " + uirecordDTOS.size());
    }

    // ✅ Function to chunk data
    private static List<List<ArabicSanData>> chunkListParallel(List<ArabicSanData> list, int chunkSize) {
        // System.out.println("[DEBUG] Chunking data into size: " + chunkSize);
        List<List<ArabicSanData>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
        return chunks;
    }
}

// ✅ `Runnable` Class for Multi-Threaded Processing
    class ChunkProcessor implements Runnable {
        Soundex soundex = new Soundex();
        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
        private final List<ArabicSanData> chunk;
        private final MultiParaSearchData multiParaSearchData;
        private final List<UiReciveSingleRecordDto> resultList;

        public ChunkProcessor(List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData, List<UiReciveSingleRecordDto> resultList) {
            this.chunk = chunk;
            this.multiParaSearchData = multiParaSearchData;
            this.resultList = resultList;
    }

    @Override
    public void run() {
        process();  // Call the process method from run()
    }
    public void process() {
        try {
            // System.out.println("[DEBUG] Thread " + Thread.currentThread().getId() + " processing chunk of size " + chunk.size());

            List<UiReciveSingleRecordDto> chunkResults = chunk.stream()
                    .map(data -> {
                        try {
                            // ✅ Ensure non-null values before passing to `calculateScores`
                            String secondName = data.getName() != null ? data.getName() : "";
                            String id2 = data.getIdValue() != null ? data.getIdValue() : "";
                            String country2 = ""; // If needed, fetch actual country data
                            String dob2 = data.getFullDate() != null ? data.getFullDate() : "";

                            // ✅ Call `calculateScores` to compute similarity
                            double[] scores = calculateScores(
                                    multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "",
                                    secondName,
                                    multiParaSearchData.getId() != null ? multiParaSearchData.getId() : "",
                                    id2,
                                    multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "",
                                    country2,
                                    multiParaSearchData.getDob() != null ? multiParaSearchData.getDob() : "",
                                    dob2);

                            // ✅ Construct DTO with calculated scores
                            return new UiReciveSingleRecordDto(data.getPersonId(), scores, data.getName(), dob2, id2, country2);
                        } catch (Exception e) {
                            System.err.println("[ERROR] Exception processing data for: " + data.getName() + " - " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull) // ✅ Remove null values
                    .collect(Collectors.toList());

            // ✅ Add results to synchronized list
            synchronized (resultList) {
                resultList.addAll(chunkResults);
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Error processing chunk: " + e.getMessage());
        }
    }

    public double[] calculateScores(String firstName, String secondName, String id1, String id2, String country1, String country2, String dob1, String dob2) {
//        // System.out.println(firstName+" : "+secondName);
        double[] scores = new double[6];

        double idMatchingScore  = IDMatch.main(id1,id2);

        scores[0] = idMatchingScore;//safeCalculate(() -> round(IDMatch.main(id1,id2), 0), "IdMatching", firstName, secondName);
        if(idMatchingScore!=100) {

            String filter1 = Preprocess.datafilter_Init(firstName);
            String filter2 = Preprocess.datafilter_Init(secondName);
            // Tokenize the names by spaces
            List<String> token1 = Functions.tokenizeString_Array(filter1);
            List<String> token2 = Functions.tokenizeString_Array(filter2);

//            // System.out.println("token1,token2" + token1 +" : "+token2);

            Map<String, List<String>>out = Functions.similarfinding( token1,token2);
//            Arrays.fill(scores, 0, 1, 5);


//            // System.out.println("after token1,token2" + token1 +" : "+token2);
            scores[1] = safeCalculate(() -> round(MatchingUtility.OnesideMatch( out), 0), "oneside", firstName, secondName);
            scores[2] = safeCalculate(() -> round(MatchingUtility.TwoSideMatch(filter1,filter2,out), 0), "exactMatch", firstName, secondName);
            scores[3] = safeCalculate(() -> round(MatchingUtility.Soundxjw(filter1,filter2,soundex,jaroWinkler), 0), "soundxJarowinkler_Match", firstName, secondName);
            scores[4] = safeCalculate(() -> round(MatchingUtility.JaroMatch(filter1,filter2,token1,token2,jaroWinkler), 0), "Jarowinkler_Match", firstName, secondName);
            scores[5] = safeCalculate(() -> round(MatchingUtility.Fwwr(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);

//            double maxScore = Arrays.stream(scores).max().orElse(Double.NEGATIVE_INFINITY);
            double maxScore = Arrays.stream(scores).max().getAsDouble();

            if(maxScore>=60)
            {
                double weight_country   = 0;//CountryMatching.main(country1, country2);
                double weight_dob       = 0;//DOBMatching.main(dob1, dob2);

                boolean country = !country1.isEmpty() && country1.length() > 0;
                boolean dob     = !dob1.isEmpty() && dob1.length() > 0;

                if(country && dob)
                {
                    weight_country  = CountryIndividualMatching.main(country1, country2);
//                    weight_dob      = DOBMatching.main(dob1, dob2);
                }
                scores[1] = scores[1] +  weight_country + weight_dob;
                scores[2] = scores[2] +  weight_country + weight_dob;
                scores[3] = scores[3] +  weight_country + weight_dob;
                scores[4] = scores[4] +  weight_country + weight_dob;
                scores[5] = scores[5] +  weight_country + weight_dob;
            }
            else {
                scores[1]=0;scores[2]=0;scores[3]=0;scores[4]=0;scores[5]=0;
            }
        }
        else {
            scores[1]=0;scores[2]=0;scores[3]=0;scores[4]=0;scores[5]=0;
        }
        return scores;
    }
    private double safeCalculate(Supplier<Double> calculation, String type, String queryCustomer, String str2) {
        try {
            Double result = calculation.get();
            return result != null ? result : 0; // Return 0 if the result is null
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private double round(double value, int places) {
        return value;
    }

    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws Exception;
    }
//    @Override
//    public void run() {
//        try {
//            // System.out.println("[DEBUG] Thread " + Thread.currentThread().getId() + " processing chunk of size " + chunk.size());
//
//            List<UiReciveSingleRecordDto> chunkResults = chunk.stream()
//                    .map(data -> new UiReciveSingleRecordDto(data.getPersonId(), new double[]{Math.random() * 100}, data.getName(), data.getFullDate(), data.getIdValue()))
//                    .collect(Collectors.toList());
//
//            // ✅ Add results to synchronized list
//            resultList.addAll(chunkResults);
//
//        } catch (Exception e) {
//            System.err.println("[ERROR] Error processing chunk: " + e.getMessage());
//        }
//    }
}
