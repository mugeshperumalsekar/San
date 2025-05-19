package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class OnBoardBatchProcessor {
//    private final ScoringCalculatorService stringsanitizeString; // String sanitization service
//
//    private final ExecutorService executorService;
//
//    public OnBoardBatchProcessor(ScoringCalculatorService stringsanitizeString,ExecutorService executorService) {
//        this.stringsanitizeString = stringsanitizeString;
//        this.executorService = executorService;
//        // System.out.println("ExecutorService pool size: " + Runtime.getRuntime().availableProcessors());
//    }
//
//
//    public void processOnBoardBatch(
//            List<ArabicSanData> batchData,
//            MultiParaSearchData multiParaSearchData,
//            List<UiReciveSingleRecordDto> uirecordDTOS) {
//
//        AtomicInteger processedCount = new AtomicInteger(0);
//
//        // Split batchData into smaller chunks (if large) for better parallelism
//        int chunkSize = 100000; // You can adjust this based on expected load and performance
//        List<List<ArabicSanData>> chunks = chunkListParallel(batchData, chunkSize);
//
//        List<CompletableFuture<List<UiReciveSingleRecordDto>>> futures = chunks.stream().parallel()
//                .map(chunk -> CompletableFuture.supplyAsync(() -> {
////                    // System.out.println("Processing chunk of size " + chunk.size()); // Log chunk size
//                    return processChunk(chunk, multiParaSearchData);
//                }, executorService).exceptionally(ex -> {
//                    System.err.println("Error processing chunk: " + ex.getMessage());
//                    return Collections.emptyList();
//                }))
//                .collect(Collectors.toList());
//
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//        allOf.join(); // Wait for all futures to complete
//
//        // After join, process the results
//        uirecordDTOS.addAll(futures.stream()
//                .map(CompletableFuture::join)
//                .flatMap(List::stream)
//                .filter(Objects::nonNull)
//                .filter(dto -> {
//                    return (Arrays.stream(dto.getOnsideMultiPara()).max().orElse(Double.NaN)>=multiParaSearchData.getScore());
//                })
//                .collect(Collectors.toList()));
//
//    }
//
//    private List<UiReciveSingleRecordDto> processChunk(List<ArabicSanData> chunk, MultiParaSearchData multiParaSearchData) {
//        return chunk.stream()
//                .map(batchData1 -> CompletableFuture.supplyAsync(() -> {
//                    try {
//                        // Processing each item
//                        // Ensure no nulls for critical data points
//                        String secondName = batchData1.getName() != null ? stringsanitizeString.sanitizeString(batchData1.getName()) : "";
//                        String country2 = ""; // If you want to use actual country, assign it here.
//                        Integer personId = batchData1.getPersonId(); // Check if it's valid if needed
//
//                        // Safely calculate the scores asynchronously(ensure there are no nulls in the parameters)
//                        double[] scores = calculateScores(
//                                multiParaSearchData.getName() != null ? multiParaSearchData.getName() : "",
//                                secondName,
//                                multiParaSearchData.getCountry() != null ? multiParaSearchData.getCountry() : "",
//                                country2
//                                );
//
//                        // Return the UiReciveSingleRecordDto object with the calculated scores
//                        return new UiReciveSingleRecordDto(personId, scores, secondName,"","", country2);
//                    } catch (Exception e) {
//                        log.error("Exception processing data for name: " + batchData1.getName(), e);
//
//                        return null; // Return null in case of failure
//                    }
//                }, executorService))  // Ensure task runs on the custom executor
//                .map(CompletableFuture::join)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
//
//
//
//    public List<List<ArabicSanData>> chunkListParallel(List<ArabicSanData> list, int chunkSize) {
//        return IntStream.range(0, (list.size() + chunkSize - 1) / chunkSize)
//                .parallel()
//                .mapToObj(i -> list.subList(i * chunkSize, Math.min((i + 1) * chunkSize, list.size())))
//                .collect(Collectors.toList());
//    }
//    // Clean up executor service when done
//    public void shutdown() {
//        executorService.shutdown();
//    }

    public static double[] calculateScores(String firstName, String secondName, String country1, String country2) {
        double[] scores = new double[5];

//            scores[0] = safeCalculate(() -> round(OnBoardOneside(firstName, secondName,  country1, country2), 0), "OnBoardOneside", firstName, secondName);
//            scores[1] = safeCalculate(() -> round(OnBoardTwoSideMatch(firstName, secondName, country1, country2), 0), "OnBoardTwoSideMatch", firstName, secondName);
//            scores[2] = safeCalculate(() -> round(OnBoardSoundexJW(firstName, secondName,country1, country2), 0), "OnBoardSoundexJW", firstName, secondName);
//            scores[3] = safeCalculate(() -> round(OnBoardJaroWinkler(firstName, secondName,  country1, country2), 0), "OnBoardJaroWinkler", firstName, secondName);
//            scores[4] = safeCalculate(() -> round(OnBoardFuzzyWR(firstName, secondName,  country1, country2), 0), "OnBoardFuzzyWR", firstName, secondName);

        return scores;
    }

    private static double safeCalculate(OnBoardBatchProcessor.Supplier<Double> calculation, String type, String queryCustomer, String str2) {
        try {
            Double result = calculation.get();
            return result != null ? result : 0; // Return 0 if the result is null
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static double round(double value, int places) {
        return value;
    }

    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws Exception;
    }
}
