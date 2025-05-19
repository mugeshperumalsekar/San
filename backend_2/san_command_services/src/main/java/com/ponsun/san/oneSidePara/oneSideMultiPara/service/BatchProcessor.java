package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class BatchProcessor {
    private final ExecutorService executorService;
    static Soundex soundex = new Soundex();
    static JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
    private static MatchingUtility matchingUtils;

    public BatchProcessor() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*32);
        // System.out.println("ExecutorService pool size: " + Runtime.getRuntime().availableProcessors());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void processBatch1(
            List<ArabicSanData> batchData,
            MultiParaSearchData multiParaSearchData,
            List<UiReciveSingleRecordDto> uirecordDTOS) {

        AtomicInteger processedCount = new AtomicInteger(0);

        // Split batchData into smaller chunks (if large) for better parallelism
//        int chunkSize = 100000; // You can adjust this based on expected load and performance
        int chunkSize = batchData.size()/(Runtime.getRuntime().availableProcessors()*2);

        List<List<ArabicSanData>> chunks = chunkListParallel(batchData, chunkSize);
//        // System.out.println("Total records chunks Process : Time"+ LocalTime.now());

        List<CompletableFuture<List<UiReciveSingleRecordDto>>> futures = chunks.stream()
                .map(chunk -> CompletableFuture.supplyAsync(() -> {
                    // Increment processed count as each chunk is processed
                    processedCount.addAndGet(chunk.size());
//                    // System.out.println("Processing chunk of size " + chunk.size()); // Log chunk size
                    return processChunk(chunk, multiParaSearchData);
                }, executorService).exceptionally(ex -> {
                    System.err.println("Error processing chunk: " + ex.getMessage());
                    return Collections.emptyList();
                }))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join(); // Wait for all futures to complete

        // After join, process the results
         uirecordDTOS.addAll(futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                 .filter(dto -> {
                     return (Arrays.stream(dto.getOnsideMultiPara()).max().orElse(Double.NaN)>=multiParaSearchData.getScore());
//                     double maxScore = Arrays.stream(dto.getOnsideMultiPara()).max().orElse(Double.NaN);
//                     return maxScore >= multiParaSearchData.getScore();  // Filter only if max score is greater than 80
                 })
//                 .filter(dto -> Arrays.stream(dto.getOnsideMultiPara()).allMatch(score -> score >= multiParaSearchData.getScore()))  // Filter only if all scores are above 80
                .collect(Collectors.toList()));
        // Shutdown the executor service
        shutdownExecutorService(executorService);

    }

    private List<UiReciveSingleRecordDto> processChunk(List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData) {
        return chunk.stream()
                .map(batchData1 -> {
                    return CompletableFuture.supplyAsync(() -> {
                        try {
                            // Processing each item
                            // Ensure no nulls for critical data points
                            String secondName = batchData1.getName(); //!= null ? stringsanitizeString.sanitizeString(batchData1.getName()) : "";
                            String dob2 = batchData1.getFullDate() != null ? batchData1.getFullDate() : "";
                            String id2 = batchData1.getIdValue() != null ? batchData1.getIdValue() : "";
                            String country2 = ""; // If you want to use actual country, assign it here.
                            Integer personId = batchData1.getPersonId(); // Check if it's valid if needed

                            double[] scores = null;

                            if (multiParaSearchData.getEntityType() == 1) {//individual
                                // Safely calculate the scores asynchronously(ensure there are no nulls in the parameters)
                                scores = calculateScores(
                                        multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "",
                                        secondName,
                                        multiParaSearchData.getId() != null ? multiParaSearchData.getId() : "",
                                        id2,
                                        multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "",
                                        country2,
                                        multiParaSearchData.getDob() != null ? multiParaSearchData.getDob() : "",
                                        dob2);
                            } else if (multiParaSearchData.getEntityType() == 2) {//corporate
                                scores = OnBoardBatchProcessor.calculateScores(
                                        multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "",
                                        secondName,
                                        multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "",
                                        country2);
                            }
                            // Return the UiReciveSingleRecordDto object with the calculated scores
                            return new UiReciveSingleRecordDto(personId, scores, batchData1.getName(), dob2, id2, country2);
                        } catch (Exception e) {
                            log.error("Exception processing data for name: " + batchData1.getName(), e);

                            return null; // Return null in case of failure
                        }
                    }, executorService);
                })  // Ensure task runs on the custom executor
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static double[] calculateScores(String firstName, String secondName, String id1, String id2, String country1, String country2, String dob1, String dob2) {
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

//          // System.out.println("token1,token2" + token1 +" : "+token2);

            Map<String, List<String>>out = Functions.similarfinding( token1,token2);

//          // System.out.println("after token1,token2" + token1 +" : "+token2);
            scores[1] = safeCalculate(() -> round(matchingUtils.OnesideMatch( out), 0), "oneside", firstName, secondName);
            scores[2] = safeCalculate(() -> round(matchingUtils.TwoSideMatch(filter1,filter2,out), 0), "exactMatch", firstName, secondName);
            scores[3] = safeCalculate(() -> round(matchingUtils.Soundxjw(filter1,filter2,soundex,jaroWinkler), 0), "soundxJarowinkler_Match", firstName, secondName);
            scores[4] = safeCalculate(() -> round(matchingUtils.JaroMatch(filter1,filter2,token1,token2,jaroWinkler), 0), "Jarowinkler_Match", firstName, secondName);
            scores[5] = safeCalculate(() -> round(matchingUtils.Fwwr(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);

//          double maxScore = Arrays.stream(scores).max().orElse(Double.NEGATIVE_INFINITY);
            double maxScore = Arrays.stream(scores).max().getAsDouble();

            if(maxScore>=60)
            {
                double weight_country   = 0;//CountryMatching.main(country1, country2);
                double weight_dob       = 0;//DOBMatching.main(dob1, dob2);

                boolean country = !country1.isEmpty() && country1.length() > 0;
                boolean dob     = !dob1.isEmpty() && dob1.length() > 0;

                if(country && dob)
                {
                    LinkedHashSet<String> d1 = Functions.dateTokenize(dob1);
                    List<Map<String, List<String>>> mapList1 = new ArrayList<>();

                    // Parse all dates for both input1 and input2 once
                    for (String date : d1) {
                        mapList1.add(DOBMatching.parseInput(date));
                    }

//                    // System.out.println("mapList1:"+mapList1);
                    LinkedHashSet<String> d2 = Functions.dateTokenize(dob2);
                    List<Map<String, List<String>>> mapList2 = new ArrayList<>();


                    for (String date : d2) {
                        mapList2.add(DOBMatching.parseInput(date));
                    }

//                    // System.out.println("mapList2:"+mapList2);

                    weight_country  = CountryIndividualMatching.main(country1, country2);
                    weight_dob      = DOBMatching.main(mapList1, mapList2);
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
        List<Double> scoreList = Arrays.stream(scores).boxed().collect(Collectors.toList());
//        // System.out.println(new double[]{Collections.max(scoreList)});

//        return scores;
        return new double[]{Collections.max(scoreList)};
    }
    private static double waitageCalculation(double similarity){
        double weight= 80;
        return (similarity / 100) * weight;
    }

    public double[] calculateScoresAtSingleOut(String firstName, String secondName, String id1, String id2, String country1, String country2, String dob1, String dob2) {
//        // System.out.println(firstName+" : "+secondName);
        double[] scores = new double[18];

//        // System.out.println("FName: "+firstName + "  - Sec Name : "+secondName);
        double idMatchingScore  = IDMatch.main(id1,id2);

        scores[0] = idMatchingScore;//safeCalculate(() -> round(IDMatch.main(id1,id2), 0), "IdMatching", firstName, secondName);
        if(idMatchingScore!=100) {

            String filter1 = Preprocess.datafilter_Init(firstName);
            String filter2 = Preprocess.datafilter_Init(secondName);
            // Tokenize the names by spaces
            List<String> token1 = Functions.tokenizeString_Array(filter1);
            List<String> token2 = Functions.tokenizeString_Array(filter2);

            Map<String, List<String>>out = Functions.similarfinding(token1,token2);

//            // System.out.println("after token1,token2" + token1 +" : "+token2);
            scores[1] = safeCalculate(() -> round(matchingUtils.OnesideMatch( out), 0), "oneside", firstName, secondName);
            scores[2] = safeCalculate(() -> round(matchingUtils.TwoSideMatch(filter1,filter2,out), 0), "exactMatch", firstName, secondName);
            scores[3] = safeCalculate(() -> round(matchingUtils.Soundxjw(filter1,filter2,soundex,jaroWinkler), 0), "soundxJarowinkler_Match", firstName, secondName);
            scores[4] = safeCalculate(() -> round(matchingUtils.JaroMatch(filter1,filter2,token1,token2,jaroWinkler), 0), "Jarowinkler_Match", firstName, secondName);
            scores[5] = safeCalculate(() -> round(matchingUtils.Fwwr(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);

            for (int i = 1; i < scores.length; i++) {
                scores[i] = Math.round(scores[i]);
            }

            scores[8]   = weight(scores[1]);
            scores[9]   = weight(scores[2]);
            scores[10]  = weight(scores[3]);
            scores[11]  = weight(scores[4]);
            scores[12]  = weight(scores[5]);//Fwwr

            //scores[12] = weight(safeCalculate(() -> round(matchingUtils.Fwwrsimlarity(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName));

            scores[13] = safeCalculate(() -> round(matchingUtils.FuzzyRatio(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);
            scores[14] = safeCalculate(() -> round(matchingUtils.FuzzyRatioWithOutToken(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);
            scores[15] = safeCalculate(() -> round(matchingUtils.FuzzyWRWithOutToken(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);
            scores[16] = safeCalculate(() -> round(matchingUtils.JW_withouttoken(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);
            scores[17] = safeCalculate(() -> round(matchingUtils.SoundexFuzzyRatio(token1,token2,soundex,jaroWinkler), 0), "soundxFuzzy_Match", firstName, secondName);

//            // System.out.println("score4 : "+scores[4]);

//            double maxScore = Arrays.stream(scores).max().orElse(Double.NEGATIVE_INFINITY);
            double maxScore = Arrays.stream(scores).max().getAsDouble();

            if(maxScore>=60)
            {
                double weight_country   = 0;//CountryMatching.main(country1, country2);
                double weight_dob       = 0;//DOBMatching.main(dob1, dob2);

//                boolean country = !country1.isEmpty() && country1.length() > 0;
//                boolean dob     = !dob1.isEmpty() && dob1.length() > 0;

                //if(country && dob)
//                {

                LinkedHashSet<String> d1 = Functions.dateTokenize(dob1);
                List<Map<String, List<String>>> mapList1 = new ArrayList<>();

                // Parse all dates for both input1 and input2 once
                for (String date : d1) {
                    mapList1.add(DOBMatching.parseInput(date));
                }

//                // System.out.println("mapList1:"+mapList1);
                LinkedHashSet<String> d2 = Functions.dateTokenize(dob2);
                List<Map<String, List<String>>> mapList2 = new ArrayList<>();


                for (String date : d2) {
                    mapList2.add(DOBMatching.parseInput(date));
                }

                // System.out.println("mapList2:"+mapList2);

                weight_country  = CountryIndividualMatching.main(country1, country2);
                weight_dob      = DOBMatching.main(mapList1, mapList2);

                    scores[6]=weight_country;
                    scores[7]=weight_dob;
//                }


//                scores[1] = scores[1] +  weight_country + weight_dob;
//                scores[2] = scores[2] +  weight_country + weight_dob;
//                scores[3] = scores[3] +  weight_country + weight_dob;
//                scores[4] = scores[4] +  weight_country + weight_dob;
//                scores[5] = scores[5] +  weight_country + weight_dob;

            }
            else {
                IntStream.range(1, 8).forEach(i -> scores[i] = 0);

//                scores[1]=0;scores[2]=0;scores[3]=0;scores[4]=0;scores[5]=0;
            }
        }
        else {
            IntStream.range(1, 8).forEach(i -> scores[i] = 0);
//            scores[1]=0;scores[2]=0;scores[3]=0;scores[4]=0;scores[5]=0;
        }
        List<Double> scoreList = Arrays.stream(scores).boxed().collect(Collectors.toList());
//        // System.out.println(Arrays.stream(scores).max().orElse(Double.NaN));
        // System.out.println(scoreList);
        return scores;
//        return Arrays.stream(scores).max().orElse(Double.NaN)+"";
    }


    public List<List<ArabicSanData>> chunkListParallel(List<ArabicSanData> list, int chunkSize) {
        return IntStream.range(0, (list.size() + chunkSize - 1) / chunkSize)
                .parallel()
                .mapToObj(i -> list.subList(i * chunkSize, Math.min((i + 1) * chunkSize, list.size())))
                .collect(Collectors.toList());
    }

    // Clean up executor service when done
    private void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    private static double safeCalculate(Supplier<Double> calculation, String type, String queryCustomer, String str2) {
        try {
            Double result = calculation.get();
            return result != null ? result : 0; // Return 0 if the result is null
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("The number of places must be non-negative.");

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static double weight(double value) {
        double weightage = 0;
        if(value>=60)
            weightage=Functions.percentage(value);
        else
            weightage=value;
        return weightage;
    }


    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws Exception;
    }
}

