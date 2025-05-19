package com.ponsun.san.algorithm.testingservice;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.UiReciveSingleRecordDto;
import com.ponsun.san.oneSidePara.oneSideMultiPara.service.FileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class UICalculationService {
//    private final ExecutorService executorService1;
    private final FileService fileService;
    private final ChunkProcessingService chunkProcessingService;


    // Constructor to inject dependencies for better testability and flexibility

//    public UICalculationService(ExecutorService executorService1, FileService fileService, ChunkProcessingService chunkProcessingService) {
//        this.executorService1 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*32);
//        this.fileService = fileService;
//        this.chunkProcessingService = chunkProcessingService;
//    }
//    @PostConstruct
//    public void init() {
//        // System.out.println("ExecutorService bean is created: " + executorService1);
//    }

//    public UICalculationService() {
//        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*32);
//
//        // System.out.println("ExecutorService pool size: " + Runtime.getRuntime().availableProcessors());
//    }
    @Async
    public CompletableFuture<List<UiReciveSingleRecordDto>> uicalculateScoreSinglePara(MultiParaSearchData multiParaSearchData) {
        List<UiReciveSingleRecordDto> uirecordDTOS = new ArrayList<>();
        List<List<ArabicSanData>> arabicSanchunkedData = fileService.readChunksFromJsonFile(multiParaSearchData.getEntityType());

        List<CompletableFuture<List<UiReciveSingleRecordDto>>> futures = new ArrayList<>();

        // For each chunk, we process it asynchronously
        for (List<ArabicSanData> chunk : arabicSanchunkedData) {
            CompletableFuture<List<UiReciveSingleRecordDto>> future = CompletableFuture.supplyAsync(() -> {
                return chunkProcessingService.processChunk(chunk, multiParaSearchData);
            });

            futures.add(future);
        }

        // Collect all results and combine them
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        // Combine all results into one list
        futures.forEach(future -> {
            try {
                uirecordDTOS.addAll(future.get());
            } catch (Exception e) {
                System.err.println("Error retrieving result: " + e);
            }
        });

        return CompletableFuture.completedFuture(uirecordDTOS);
    }
}
