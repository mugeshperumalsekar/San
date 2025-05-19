package com.ponsun.san.algorithm.service;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.oneSidePara.oneSideMultiPara.service.BatchProcessor;
import com.ponsun.san.oneSidePara.oneSideMultiPara.service.CoperateBatchProcessor;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@Service
public class BatchProcessorTesting {
    static Soundex soundex = new Soundex();
    static JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
    private static AtomicInteger processedCount = new AtomicInteger(0);  // Thread-safe processed count
    private static MatchingUtility matchingUtils;
    public static List<UiReciveSingleRecordDto> processChunksInParallelWithFilter(
            List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData,String filter1,
            List<String> token1,List<Map<String, List<String>>> mapList1,String name1_withoutsuf,List<String> token1_withoutsuf ) {

        List<UiReciveSingleRecordDto> uirecordDTOS = chunk.stream()
                .map(batchData -> processSingleRecord(batchData, multiParaSearchData, filter1, token1,mapList1,name1_withoutsuf,token1_withoutsuf))
                .filter(Objects::nonNull) // Filter out any null results from the processing
                .flatMap(List::stream) // Flatten the list of lists into a single list
                .filter(dto -> isValidScore(dto, multiParaSearchData))
                .collect(Collectors.toList());


//        List<UiReciveSingleRecordDto> uirecordDTOS = chunk.stream()
//                .map(batchData -> processSingleRecord(batchData, multiParaSearchData, filter1, token1, mapList1, name1_withoutsuf, token1_withoutsuf))
//                // Flatten the list (if processSingleRecord returns a list of UiReciveSingleRecordDto)
//                .flatMap(List::stream)
//                .filter(dto -> {
//                    // Get the maximum value from the onsideMultiPara array in each dto
//                    double maxScore = Arrays.stream(dto.getOnsideMultiPara())
//                            .max()
//                            .orElse(Double.NaN);  // Return Double.NaN if the array is empty
//
//                    // Debugging: print maxScore and the array contents
//                    System.out.println("Max Score: " + maxScore);
//                    System.out.println("OnsideMultiPara: " + Arrays.toString(dto.getOnsideMultiPara()));
//
//                    // Check if the max value is greater than or equal to the threshold score
//                    return maxScore >= multiParaSearchData.getScore();
//                })
//                .collect(Collectors.toList());

        return uirecordDTOS;
    }
    public static List<UiReciveSingleRecordDto> processEntityChunksInParallelWithFilter(LinkedHashSet<String> suffixes,
            String name1_withoutsuf,List<String> token1_withoutsuf,List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData,String filter1,
            List<String> token1) {
        List<UiReciveSingleRecordDto> uirecordDTOS = chunk.stream()
                .map(batchData -> processEntitySingleRecord(batchData, multiParaSearchData, filter1, token1,name1_withoutsuf,token1_withoutsuf))
                .filter(Objects::nonNull) // Filter out any null results from the processing
                .flatMap(List::stream) // Flatten the list of lists into a single list
                .filter(dto -> isValidScore(dto, multiParaSearchData))
                .collect(Collectors.toList());



        return uirecordDTOS;
    }
    public static List<UiReciveSingleRecordDto> processEntityRemitChunksInParallelWithFilter(
            List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData,String filter1,
            List<String> token1) {

        List<UiReciveSingleRecordDto> uirecordDTOS = chunk.stream()
                .map(batchData -> processEntitySingleRecord(batchData, multiParaSearchData, filter1, token1))
                .filter(Objects::nonNull) // Filter out any null results from the processing
                .flatMap(List::stream) // Flatten the list of lists into a single list
                .filter(dto -> isValidScore(dto, multiParaSearchData))
                .collect(Collectors.toList());

        return uirecordDTOS;
    }
    public static List<UiReciveSingleRecordDto> processChunksInParallel(
            List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData) {

        String filter1              = Preprocess.datafilter_Init(multiParaSearchData.getName());
        List<String> token1         = Functions.tokenizeString_Array(filter1);
        LinkedHashSet<String> d1    = Functions.dateTokenize(multiParaSearchData.getDob());
        List<Map<String, List<String>>> mapList1 = new ArrayList<>();
        for (String date : d1) {
            mapList1.add(DOBMatching.parseInput(date));
        }

        List<UiReciveSingleRecordDto> uirecordDTOS = chunk.stream()
                .map(batchData -> processSingleRecord(batchData, multiParaSearchData, filter1, token1,mapList1,"", Collections.singletonList("")))
                .filter(Objects::nonNull) // Filter out any null results from the processing
                .flatMap(List::stream) // Flatten the list of lists into a single list
                .filter(dto -> isValidScore(dto, multiParaSearchData))
                .collect(Collectors.toList());

        return uirecordDTOS;
    }
    private static boolean isValidScore(UiReciveSingleRecordDto dto, MultiParaSearchData multiParaSearchData) {
        // Check if the dto and its onsideMultiPara are valid, and if the max value is greater than or equal to the threshold score
        return Arrays.stream(dto.getOnsideMultiPara())
                .filter(Objects::nonNull)
                .max()
                .orElse(Double.NaN) >= multiParaSearchData.getScore();



    }

    private static List<UiReciveSingleRecordDto> processSingleRecord(ArabicSanData batchData, MultiParaSearchData multiParaSearchData,String filter1,List<String> token1,List<Map<String, List<String>>> mapdate1List,String  name1_withoutsuf,List<String> token1_withoutsuf) {
        try {
            String firstName = multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "";
            String id1       = multiParaSearchData.getId() != null ? multiParaSearchData.getId() : "";
            String country1  = multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "";
            String dob1      = multiParaSearchData.getDob() != null ? multiParaSearchData.getDob() : "";

            // Ensure no nulls for critical data points
            String secondName   = batchData.getName() != null ? batchData.getName() : "";
            String dob2         = batchData.getFullDate() != null ? batchData.getFullDate() : "";
            String id2          = batchData.getIdValue() != null ? batchData.getIdValue() : "";
            String country2     = batchData.getCountryName() != null ? batchData.getCountryName() : "";  // Ensure country2 is non-null
            Integer personId    = batchData.getPersonId();  // Check if it's valid if needed

            ////////////////////////////////2.11
            String filter2 = Preprocess.datafilter_Init(batchData.getName());
            List<String> token2 = Functions.tokenizeString_Array(filter2);
            Map<String, List<String>> out = Functions.similarfinding( token1,token2);
            ///////////////////////////
            int entityType = multiParaSearchData.getEntityType();
            double[] scores = null;
            switch (entityType) {
                case 1:
                    scores = calculateScores(firstName,  secondName,  id1,  id2,  country1,  country2, dob1, dob2,filter1,token1,filter2,token2,out,mapdate1List);
                    break;
                case 3:
                    scores = IndividualRemittanceMatchesBatchProcessor.IndividualRemittanceMatches( id1, id2, country1, country2,
                            filter1, token1, filter2, token2,
                            out, name1_withoutsuf,token1_withoutsuf);
                    break;
                case 4:
                    scores = EntityRemittanceBatchProcessor.EntityRemittanceMatches(firstName,  secondName,  id1,  id2,  country1,  country2, filter1,token1,filter2,token2,out);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity type: " + entityType);
            }
            double maxScore = Arrays.stream(scores, 1, scores.length).parallel().max().orElse(0);
            // Return the UiReciveSingleRecordDto object with the calculated scores
            return List.of(new UiReciveSingleRecordDto(personId, new double[]{maxScore}, batchData.getName(), dob2, id2, country2));
        } catch (Exception e) {
            //Logger.error("Exception processing data for name: " + batchData.getName(), e);
            // System.out.println("Exception processing data for name: " + batchData.getName() +":"+ e);
            return Collections.emptyList();  // Return empty list in case of failure
        }
    }
    private static List<UiReciveSingleRecordDto> processEntitySingleRecord(ArabicSanData batchData, MultiParaSearchData multiParaSearchData,String filter1,List<String> token1,String name1_withoutsuf,List<String> token1_withoutsuf ) {
        try {
            String firstName = multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "";
            String id1       = multiParaSearchData.getId() != null ? multiParaSearchData.getId() : "";
            String country1  = multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "";

            // Ensure no nulls for critical data points
            String secondName   = batchData.getName() != null ? batchData.getName() : "";
            String dob2         = batchData.getFullDate() != null ? batchData.getFullDate() : "";
            String id2          = batchData.getIdValue() != null ? batchData.getIdValue() : "";
            String country2     = batchData.getCountryName() != null ? batchData.getCountryName() : "";  // Ensure country2 is non-null
            Integer personId    = batchData.getPersonId();  // Check if it's valid if needed

            ////////////////////////////////2.11
            String filter2 = Preprocess.datafilter_Init(batchData.getName());
            List<String> token2 = Functions.tokenizeString_Array(filter2);
            Map<String, List<String>> out = Functions.similarfinding( token1,token2);
            ///////////////////////////

            double[] scores = EntityOnboardingBatchProcessor.EntityOnboarding(  id1,  id2,  country1,  country2, filter1,token1,filter2,token2,out
                     ,name1_withoutsuf, token1_withoutsuf);

            double maxScore = Arrays.stream(scores, 1, scores.length).parallel().max().orElse(0);
            // Return the UiReciveSingleRecordDto object with the calculated scores
            return List.of(new UiReciveSingleRecordDto(personId, new double[]{maxScore}, batchData.getName(), dob2, id2, country2));
        } catch (Exception e) {
            //Logger.error("Exception processing data for name: " + batchData.getName(), e);
            // System.out.println("Exception processing data for name: " + batchData.getName() +":"+ e);
            return Collections.emptyList();  // Return empty list in case of failure
        }
    }

    private static List<UiReciveSingleRecordDto> processEntitySingleRecord(ArabicSanData batchData, MultiParaSearchData multiParaSearchData,String filter1,List<String> token1) {
        try {
            String firstName = multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "";
            String id1       = multiParaSearchData.getId() != null ? multiParaSearchData.getId() : "";
            String country1  = multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "";

            // Ensure no nulls for critical data points
            String secondName   = batchData.getName() != null ? batchData.getName() : "";
            String dob2         = batchData.getFullDate() != null ? batchData.getFullDate() : "";
            String id2          = batchData.getIdValue() != null ? batchData.getIdValue() : "";
            String country2     = batchData.getCountryName() != null ? batchData.getCountryName() : "";  // Ensure country2 is non-null
            Integer personId    = batchData.getPersonId();  // Check if it's valid if needed

            ////////////////////////////////2.11
            String filter2      = Preprocess.datafilter_Init(batchData.getName());
            List<String> token2 = Functions.tokenizeString_Array(filter2);
            Map<String, List<String>> out = Functions.similarfinding( token1,token2);
            ///////////////////////////
            double[] scores = EntityRemittanceBatchProcessor.EntityRemittanceMatches(firstName,  secondName,  id1,  id2,  country1,  country2, filter1,token1,filter2,token2,out);

            // Return the UiReciveSingleRecordDto object with the calculated scores
            return List.of(new UiReciveSingleRecordDto(personId, scores, batchData.getName(), dob2, id2, country2));
        } catch (Exception e) {
            //Logger.error("Exception processing data for name: " + batchData.getName(), e);
            // System.out.println("Exception processing data for name: " + batchData.getName() +":"+ e);
            return Collections.emptyList();  // Return empty list in case of failure
        }
    }
    // Example method to simulate retrieving chunks (replace with actual data)
    public static double[] calculateScores(String firstName, String secondName, String id1, String id2, String country1, String country2, String dob1, String dob2,
                                           String filter1,List<String> token1,String filter2,List<String> token2,Map<String, List<String>> out,List<Map<String, List<String>>> mapdate1List) {
        double[] scores = new double[11];

        double idMatchingScore  = IDMatch.main(id1,id2);

        scores[0] = idMatchingScore;//safeCalculate(() -> round(IDMatch.main(id1,id2), 0), "IdMatching", firstName, secondName);
        if(idMatchingScore!=100) {
            scores[1] = safeCalculate(() -> round(matchingUtils.OnesideMatch( out), 0), "oneside", firstName, secondName);
            scores[2] = safeCalculate(() -> round(matchingUtils.TwoSideMatch(filter1,filter2,out), 0), "exactMatch", firstName, secondName);
            scores[3] = safeCalculate(() -> round(matchingUtils.Soundxjw(filter1,filter2,soundex,jaroWinkler), 0), "soundxJarowinkler_Match", firstName, secondName);
            scores[4] = safeCalculate(() -> round(matchingUtils.JaroMatch(filter1,filter2,token1,token2,jaroWinkler), 0), "Jarowinkler_Match", firstName, secondName);
            scores[5] = safeCalculate(() -> round(matchingUtils.Fwwr(filter1,filter2,token1,token2), 0), "Fuzzy_WeightedRatio", firstName, secondName);

            Arrays.parallelSetAll(scores, i -> (i == 0) ? scores[i] : Math.round(scores[i]));
            double maxScore = Arrays.stream(scores, 1, scores.length).parallel().max().orElse(0);

            if (maxScore >= 60) {
                applyCountryAndDobWeights(scores,country1, country2, dob1, dob2,mapdate1List);
            } else {
                Arrays.fill(scores, 6, scores.length, 0);
            }
        }
        else {
            Arrays.fill(scores, 1, scores.length, 0);
        }

//        double maxScore = Arrays.stream(scores)
//                .max()
//                .orElse(Double.NaN); // Return Double.NaN if the array is empty
        return scores;
        //return new double[]{Collections.max(Arrays.stream(scores).boxed().collect(Collectors.toList()))};
    }

    private static void applyCountryAndDobWeights(double[] computedScores, String country1, String country2, String dob1, String dob2,List<Map<String, List<String>>> mapdate1List) {
        double weight_country = (country1 == null || country1.isEmpty()) ? 0 : CountryIndividualMatching.main(country1, country2);
        double weight_dob = (dob1 == null || dob1.isEmpty()) ? 0 : calculateDOBWeight(mapdate1List, dob2);

        // Parallel update for scores
        IntStream.range(6, computedScores.length).parallel().forEach(i ->
                computedScores[i] = weight(computedScores[i]) + weight_country + weight_dob
        );
    }

    private static double calculateDOBWeight(List<Map<String, List<String>>> mapdate1List, String dob2) {
        LinkedHashSet<String> d2 = Functions.dateTokenize(dob2);
        List<Map<String, List<String>>> mapList2 = new ArrayList<>();

        for (String date : d2) {
            mapList2.add(DOBMatching.parseInput(date));
        }

        return DOBMatching.main(mapdate1List, mapList2);
    }

    private static double weight(double value) {
        double weightage = 0;
        if(value>=60)
            weightage=Functions.percentage(value);
        else
            weightage=value;
        return weightage;
    }

    private static double safeCalculate(BatchProcessorTesting.Supplier<Double> calculation, String type, String queryCustomer, String str2) {
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

    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws Exception;
    }
//////////////////////////////////////
}
