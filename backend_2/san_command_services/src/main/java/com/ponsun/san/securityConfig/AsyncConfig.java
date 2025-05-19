package com.ponsun.san.securityConfig;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

//@Configuration
//public class AsyncConfig {
//
//    @Bean
//    public ExecutorService executorService() {
//        // Create a ThreadPoolTaskExecutor, which is a type of ExecutorService
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//
//        // Configure the thread pool (you can adjust the parameters based on your requirements)
//        executor.setCorePoolSize(4); // Minimum number of threads
//        executor.setMaxPoolSize(10); // Maximum number of threads
//        executor.setQueueCapacity(500); // Queue capacity
//        executor.setThreadNamePrefix("Async-Thread-"); // Thread naming for debugging
//        executor.initialize(); // Initialize the executor
//        return executor.getThreadPoolExecutor();
//    }
//}

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Increase the core pool size as needed
        executor.setMaxPoolSize(100);  // Increase the max pool size if necessary
        executor.setQueueCapacity(200); // Increase the queue capacity

        executor.setThreadNamePrefix("async-task-");
        executor.initialize();
        return executor;
    }
    @PostConstruct
    public void logExecutorService() {
        // System.out.println("ExecutorService bean created: " + getAsyncExecutor());
    }
}