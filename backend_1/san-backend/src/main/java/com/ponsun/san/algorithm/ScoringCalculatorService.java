package com.ponsun.san.algorithm;

import com.ponsun.san.EhcachePOC.Data.OFACData;
import com.ponsun.san.dto.RecordDTO;

import com.ponsun.san.ofac.Count.data.SanctionDetailData;
import com.ponsun.san.ofac.Count.service.RecordDetails;
import com.ponsun.san.ofac.Count.service.RecordReadService;
import com.ponsun.san.ofac.Count.service.SanctionDetailReadService;
import com.ponsun.san.searchLifcycle.hitrecord.domain.HitRecord;
import com.ponsun.san.searchLifcycle.hitrecord.domain.HitRecordRepository;
import com.ponsun.san.searchLifcycle.hitrecord.request.CreateHitRecordRequest;
import com.ponsun.san.searchLifcycle.hitrecord.services.HitRecordWritePlatformService;
import com.ponsun.san.uiTest.AlgorithmTesting.ExactMatch.ExactMatch;
import com.ponsun.san.uiTest.AlgorithmTesting.Fuzzy.Fuzzy_WeightedRatio;
import com.ponsun.san.uiTest.AlgorithmTesting.Jaro.Jarowinkler_Match;
import com.ponsun.san.uiTest.AlgorithmTesting.Oneside.Oneside;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;



import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;
@Service
@Slf4j
@RequiredArgsConstructor
public class ScoringCalculatorService {
    private final AlgorithmRule algorithmRule;
    private final RecordReadService recordReadService;
    private final RecordDetails recordDetailsComponent;
    private final HitRecordRepository hitdataRepository;
    private final SanctionDetailReadService sanreadService;

//    @Async("asyncExecutor")
//    public Future<List<RecordDTO>> calculatenamewiseScore(String queryCustomer, double matchingScore, Integer searchId, List<OFACData> countDataList) {
//        // Sanitize queryCustomer
//        // queryCustomer = sanitizeString(queryCustomer);
//
//        // Process countDataList using streams
//        String finalQueryCustomer = sanitizeString(queryCustomer);
//        List<RecordDTO> listOfArrays = countDataList.parallelStream()
//                .map(countData -> {
//                    try {
//                        String sanitizedName = sanitizeString(countData.getName());
//                        double[] scores = calculateScores(finalQueryCustomer, sanitizedName);
//
//                        if (algorithmRule.isCriminalRulePassed(scores[0], scores[1], scores[2], scores[3], matchingScore)) {
//                            double maxScore = findMaxScore(scores);
//                            return createRecordDTO(countData, searchId, maxScore);
//                        }
//                        return null;
//                    } catch (Exception e) {
////                    logger.severe("Exception processing data: " + e.getMessage());
//                        return null; // Return null if an exception occurs
//                    }
//                })
//                .filter(recordDTO -> recordDTO != null)
//                .collect(Collectors.toList());
//
//        return new AsyncResult<>(listOfArrays);
//    }
//@Async("asyncExecutor")
//public CompletableFuture<List<RecordDTO>> calculatenamewiseScore(String queryCustomer, double matchingScore, Integer searchId, List<OFACData> countDataList) {
//    // Sanitize queryCustomer
//    String finalQueryCustomer = sanitizeString(queryCustomer);
//
//    // Process countDataList using streams
//    List<RecordDTO> listOfArrays = countDataList.stream()//.parallelStream()
//            .map(countData -> {
//                try {
//                    String sanitizedName = sanitizeString(countData.getName());
//                    double[] scores = calculateScores(finalQueryCustomer, sanitizedName);
//
//                    if (algorithmRule.isCriminalRulePassed(scores[0], scores[1], scores[2], scores[3], matchingScore)) {
//                        double maxScore = findMaxScore(scores);
//                        return createRecordDTO(countData, searchId, maxScore);
//                    }
//                    return null;
//                } catch (Exception e) {
//                    log.warn("Exception processing data: " + e.getMessage());
//                    return null; // Return null if an exception occurs
//                }
//            })
//            .filter(Objects::nonNull) // More concise null filtering
//            .collect(Collectors.toList());
//
//    return CompletableFuture.completedFuture(listOfArrays);
//}
    ///////////////////////////////////////////////////////////////////
//@Async("asyncExecutor")
//public CompletableFuture<List<RecordDTO>> calculatenamewiseScore(String queryCustomer, double matchingScore, Integer searchId, List<OFACData> countDataList) {
//    // Sanitize queryCustomer once
//    String sanitizedQueryCustomer = sanitizeString(queryCustomer);
//
//    // Process countDataList in parallel and return results
//    List<RecordDTO> results = countDataList.parallelStream()
//            .map(countData -> processCountData(countData, sanitizedQueryCustomer, matchingScore, searchId))
//            .filter(Objects::nonNull)
//            .collect(Collectors.toList());
//
//    return CompletableFuture.completedFuture(results);
//}
//
//    private RecordDTO processCountData(OFACData countData, String queryCustomer, double matchingScore, Integer searchId) {
//        try {
//            String sanitizedName = sanitizeString(countData.getName());
//            System.out.println("Name : "+sanitizedName);
//            double[] scores = calculateScores(queryCustomer, sanitizedName);
//            System.out.println("scores : {}"+Arrays.toString(scores));
//
//            if (algorithmRule.isCriminalRulePassed(scores[0], scores[1], scores[2], scores[3], matchingScore)) {
//                double maxScore = Math.min(findMaxScore(scores), 100);
//                System.out.println("Out : "+countData +" : "+maxScore);
//                return createRecordDTO(countData, searchId, maxScore);
//            }
//            return null;
//        } catch (Exception e) {
//            log.error("Exception processing data for name: " + countData.getName(), e);
//            return null;
//        }
//    }
//    private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z]+$");
//
//    public static boolean isEnglish(String input) {
//        return input != null && ENGLISH_PATTERN.matcher(input).matches();
//    }
//    private String sanitizeString(String input) {
//        return input.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
//    }
//
//    private double[] calculateScores(String queryCustomer, String sanitizedName) {
//        //ExactMatch.ExactMatching(queryCustomer, sanitizedName);
//        return new double[]{
//                safeCalculate(() -> round(Oneside.onesideMatching(queryCustomer, sanitizedName), 0), "oneside", queryCustomer, sanitizedName),
//                safeCalculate(() -> round(0, 0), "exactMatch", queryCustomer, sanitizedName),
////                                safeCalculate(() -> round(ExactMatch.ExactMatching(queryCustomer, sanitizedName), 0), "exactMatch", queryCustomer, sanitizedName),
//                safeCalculate(() -> round(Jarowinkler_Match.getJarowinklerMatching(queryCustomer, sanitizedName), 0), "Jarowinkler_Match", queryCustomer, sanitizedName),
//                safeCalculate(() -> round(Fuzzy_WeightedRatio.Fuzzy_WeightedRatio(queryCustomer, sanitizedName), 0), "Fuzzy_WeightedRatio", queryCustomer, sanitizedName)
//        };
//    }
//
//    private double safeCalculate(Supplier<Double> calculation, String type, String queryCustomer, String str2) {
//        try {
//            return calculation.get();
//        } catch (Exception e) {
//            log.error("Exception during " + type + " calculation for: " + queryCustomer + " and " + str2, e);
//            return 0;
//        }
//    }
//
//    private double findMaxScore(double[] scores) {
//        return Arrays.stream(scores).max().orElse(0);
//    }
//
//
//    private RecordDTO createRecordDTO(OFACData countData, Integer searchId, double score) {
//        RecordDTO recordDTO = new RecordDTO();
//        recordDTO.setIds(countData.getId());
//        recordDTO.setNAME(countData.getName());
//        recordDTO.setFileList(countData.getFileList());
//        recordDTO.setFileType(countData.getFileType());
//        recordDTO.setCriminalId(countData.getId());
//        recordDTO.setSearchId(searchId);
//        recordDTO.setScore(score);
//        return recordDTO;
//    }
    //////////////////////////////////////////////////////////////////
@Async("asyncExecutor")
public CompletableFuture<List<RecordDTO>> calculatenamewiseScore(String queryCustomer, double matchingScore, Integer searchId, List<OFACData> countDataList) {
    // Sanitize queryCustomer
    String finalQueryCustomer = sanitizeString(queryCustomer);
    // Example of thread-safe accumulator
    AtomicInteger processedCount = new AtomicInteger(0);

//    List<RecordDTO> listOfArrays = countDataList.parallelStream()
        List<RecordDTO> listOfArrays = countDataList.stream()//.stream()//.parallelStream()
            .map(countData -> {
                try {
                    if (countData == null || countData.getName() == null) {
                        log.warn("Skipping null countData or name: " + countData);
                        return null;
                    }
                    String sanitizedName = sanitizeString(countData.getName());

                    if (sanitizedName == null || sanitizedName.isEmpty()) {
                        log.warn("Sanitized name is null or empty for countData: " + countData);
                        return null;
                    }
//                    System.out.println("Name : "+sanitizedName);
                    double[] scores = calculateScores(finalQueryCustomer, sanitizedName);
//                    System.out.println("scores : {}"+Arrays.toString(scores));

                    boolean rulePassed = algorithmRule.isCriminalRulePassed(scores[0], scores[1], scores[2],0, matchingScore);
                    if (rulePassed)
                    {
//                        System.out.println("Out : "+countData );
                        double maxScore = findMaxScore(scores);
                        processedCount.incrementAndGet(); // Thread-safe increment
//                        if(maxScore>100)
//                            maxScore=100;
//                        System.out.println("Out val: "+countData.getName() +" : "+maxScore);
                        return createRecordDTO(countData, searchId, maxScore);
                    }
                    return null;
                } catch (Exception e) {
                    log.error("Exception processing data for name: " + countData.getName(), e);
                    return null;
                }
            })
            .filter(Objects::nonNull) // Filter out nulls
            .collect(Collectors.toList());

    return CompletableFuture.completedFuture(listOfArrays);
}
    private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    public static boolean isEnglish(String input) {
        return input != null && ENGLISH_PATTERN.matcher(input).matches();
    }

    //Tika tika = new Tika();
//    public String identifyLanguage(String text) {
//        LanguageIdentifier identifier = new LanguageIdentifier(text);
//        return identifier.getLanguage();
//    }

    public String sanitizeString(String input) {

        String str  =  input.replace("-"," ");

        return str.replaceAll(",", " ")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll("[-+^]*", "")
                .replaceAll("_"," ")
                .toLowerCase();
    }
    public static String removeNonAlphabetic(String input) {
        if (input == null) {
            return null; // or return an empty string if preferred
        }
        return input.replaceAll("[^a-zA-Z]", "");
    }
    private double[] calculateScores(String queryCustomer, String str2) {
        return new double[]{

                safeCalculate(() -> round(Oneside.onesideMatching(queryCustomer, str2), 0), "oneside", queryCustomer, str2),
                safeCalculate(() -> round(ExactMatch.ExactMatching(queryCustomer, str2), 0), "exactMatch", queryCustomer, str2),
                safeCalculate(() -> round(Jarowinkler_Match.getJarowinklerMatching(queryCustomer, str2), 0), "Jarowinkler_Match", queryCustomer, str2),
//                safeCalculate(() -> round(Fuzzy_WeightedRatio.Fuzzy_WeightedRatio(queryCustomer, str2), 0), "Fuzzy_WeightedRatio", queryCustomer, str2)
        };
//        double[] scores = new double[4];
//        scores[0] = safeCalculate(() -> round(Oneside.onesideMatching(queryCustomer, str2), 0), "oneside", queryCustomer, str2);
//        scores[1] = safeCalculate(() -> round(ExactMatch.ExactMatching(queryCustomer, str2), 0), "exactMatch", queryCustomer, str2);
//        scores[2] = safeCalculate(() -> round(Jarowinkler_Match.getJarowinklerMatching(queryCustomer, str2), 0), "Jarowinkler_Match", queryCustomer, str2);
//        scores[3] = safeCalculate(() -> round(Fuzzy_WeightedRatio.Fuzzy_WeightedRatio(queryCustomer, str2), 0), "Fuzzy_WeightedRatio", queryCustomer, str2);
//        return scores;
    }

    private double safeCalculate(Supplier<Double> calculation, String type, String queryCustomer, String str2) {
        try {
            return calculation.get();
        } catch (IndexOutOfBoundsException e) {
            //System.out.println();
            //log.info("safeCalculate call"+type + ":" + queryCustomer + " " + str2 + "  " + e);
            return 0; // Return default value if error occurs
        }
    }

    private double findMaxScore(double[] scores) {
        return Arrays.stream(scores).max().orElse(0);

//        double maxScore = scores[0];
//        for (double score : scores) {
//            if (score > maxScore) {
//                maxScore = score;
//            }
//        }
//        return Math.round(maxScore);
    }

    private RecordDTO createRecordDTO(OFACData countData, Integer searchId, double score) {
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setIds(countData.getId());
        recordDTO.setNAME(countData.getName());
        recordDTO.setFileList(countData.getFileList());
        recordDTO.setFileType(countData.getFileType());
        recordDTO.setCriminalId(countData.getId());
        recordDTO.setSearchId(searchId);
        recordDTO.setScore(score);
        return recordDTO;
    }

    // Utility method to round values
    private double round(double value, int places) {
        // Implementation for rounding
        // (You can use BigDecimal or another method here)
        return value;
    }

    // Functional interface for lambda
    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws IndexOutOfBoundsException;
    }

    public List<RecordDTO> processAndSaveRecords(List<RecordDTO> recordDTOList, Integer searchId) {
        List<HitRecord> hitRecords = new ArrayList<>();

        for (RecordDTO addressDTO : recordDTOList) {
            hitRecords.add(processRecordDTO(searchId, addressDTO));
        }
        // Save all records in a single batch operation
        List<HitRecord> savedHitRecords = new ArrayList<>();
        savedHitRecords = this.hitdataRepository.saveAll(hitRecords);

//        System.out.println("savedHitRecords : " + savedHitRecords);
//        System.out.println("recordDTOList : " + recordDTOList);

        List<RecordDTO> newRecordDTO = new ArrayList<>();

        try {
            copyProperties(savedHitRecords, recordDTOList, newRecordDTO);
//            System.out.println("newRecordDTO :" + newRecordDTO);
            updateRecordDTOIfNeeded(recordDTOList, newRecordDTO);
//            System.out.println("Final newRecordDTO:" + newRecordDTO);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return newRecordDTO;
    }

    private HitRecord processRecordDTO(Integer searchId, RecordDTO recordDTO) {
        CreateHitRecordRequest request = createHitRecordRequest(searchId, recordDTO);
        System.out.println(request);
        HitRecord hitRecord = HitRecord.create(request);
        return hitRecord;
    }

    private void copyProperties(List<HitRecord> savedHitRecords, List<RecordDTO> sourceList, List<RecordDTO> targetList) {
        if (sourceList.size() != savedHitRecords.size()) {
            throw new IllegalArgumentException("Source and HitRecordstarget lists must be of the same size.");
        }

        for (int i = 0; i < sourceList.size(); i++) {
            RecordDTO source = sourceList.get(i);
            HitRecord HitIdsource = savedHitRecords.get(i);
            RecordDTO target = new RecordDTO();
            copyProperties(source, target, HitIdsource);
            targetList.add(target);
        }
    }

    private void copyProperties(RecordDTO source, RecordDTO target, HitRecord HitIdsource) {
        target.setHitId(HitIdsource.getId());
        target.setIds(source.getIds());
        target.setSearchId(source.getSearchId());
        target.setCriminalId(source.getIds());
        target.setNAME(source.getNAME().trim());
        target.setFileType(source.getFileType());
        target.setFileList(source.getFileList());
        target.setScore(source.getScore());
    }

    private CreateHitRecordRequest createHitRecordRequest(Integer searchId, RecordDTO recordDTO) {
        CreateHitRecordRequest request = new CreateHitRecordRequest();

        request.setName(recordDTO.getNAME());
        request.setSearchId(searchId);
        request.setCriminalId(recordDTO.getCriminalId());
        request.setDisplay("P" + recordDTO.getIds());
        request.setFileType(recordDTO.getFileType());
        request.setMatchingScore(recordDTO.getScore());
        request.setCycleId(1);
        request.setStatusNowId(0);

        return request;
    }
//    private void updateRecordDTOIfNeeded(List<RecordDTO> sourceList, List<RecordDTO> targetList) throws ExecutionException, InterruptedException {
//        for (int i = 0; i < sourceList.size(); i++) {
//            RecordDTO source = sourceList.get(i);
//            RecordDTO target = targetList.get(i);
//
//            recordReadService.updateRecordDTO(recordDetailsComponent, source.getIds(), target);
//            targetList.set(i, target);
//        }
//    }
//    private RecordDTO getFutureResult(Future<RecordDTO> future) {
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    ///////////////////////////////////////////////////////////////
    private void updateRecordDTOIfNeeded(List<RecordDTO> sourceList, List<RecordDTO> targetList) throws ExecutionException, InterruptedException {
        if (sourceList == null || targetList == null) {
            throw new IllegalArgumentException("Source or target list is null");
        }
        // Parallelism level can be adjusted as needed
        ForkJoinPool pool = new ForkJoinPool();


        // Submit a recursive task to the pool
        pool.submit(new UpdateTask(sourceList, targetList, 0, sourceList.size())).get();
        // Shutdown the pool
        pool.shutdown();
    }

    private class UpdateTask extends RecursiveAction {
        private static final int THRESHOLD = 100; // Adjust threshold based on performance testing
        private final List<RecordDTO> sourceList;
        private final List<RecordDTO> targetList;
        private final int start;
        private final int end;

        UpdateTask(List<RecordDTO> sourceList, List<RecordDTO> targetList, int start, int end) {
            this.sourceList = sourceList;
            this.targetList = targetList;
            this.start = start;
            this.end = end;
        }

        @SneakyThrows
        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                // Process records in this range
                for (int i = start; i < end; i++) {
                    RecordDTO source = sourceList.get(i);
                    RecordDTO target = targetList.get(i);
                    Integer fileType    = source.getFileType();
                    Integer ids = source.getIds();

                    if(fileType==1)//ofac
                        recordReadService.updateRecordDTO(recordDetailsComponent, source.getIds(), target);
                    else {//all othr san
                        SanctionDetailData list = sanreadService.fetchSanctionData(ids, fileType);
                        copyProperties(list,target);
                        }
                    targetList.set(i, target);
                }
            } else {
                // Split task and process in parallel
                int mid = (start + end) / 2;
                invokeAll(new UpdateTask(sourceList, targetList, start, mid),
                        new UpdateTask(sourceList, targetList, mid, end));
            }
        }
        private void copyProperties(SanctionDetailData source, RecordDTO target) {
            if (source == null) {
             //System.out.println(source);

                target.setNationality("");
                target.setCitizenship("");
                target.setAddress("");
                target.setEntityType("");
                target.setPassport("");
                target.setProgram("");
//                throw new IllegalArgumentException("Source object cannot be null");
            }
            else {
                String nationality = source.getNationality_country();
                String citizenship = source.getCiti_country();
                String address = source.getAddr_country();
                String entityType = source.getGroup_Type();
                String passport = source.getIden_country();
                String program = source.getGroup_Type();

//                System.out.println("Nationality: " + nationality);
//                System.out.println("Citizenship: " + citizenship);
//                System.out.println("Address: " + address);
//                System.out.println("Entity Type: " + entityType);
//                System.out.println("Passport: " + passport);
//                System.out.println("Program: " + program);

                target.setNationality(nationality != null ? nationality : "");
                target.setCitizenship(citizenship != null ? citizenship : "");
                target.setAddress(address != null ? address : "");
                target.setEntityType(entityType != null ? entityType : "");
                target.setPassport(passport != null ? passport : "");
                target.setProgram(program != null ? program : "");
            }
//            target.setNationality(source.getNationality_country() != null ? source.getNationality_country() : "");
//            target.setCitizenship(source.getCiti_country() != null ? source.getCiti_country() : "");
//            target.setAddress(source.getAddr_country() != null ? source.getAddr_country() : "");
//            target.setEntityType(source.getGroup_Type() != null ? source.getGroup_Type() : "");
//            target.setPassport(source.getIden_country() != null ? source.getIden_country() : "");
//            target.setProgram(source.getGroup_Type() != null ? source.getGroup_Type() : "");
        }
    }
    /////////////////////////////////////////////////////////////////
    }