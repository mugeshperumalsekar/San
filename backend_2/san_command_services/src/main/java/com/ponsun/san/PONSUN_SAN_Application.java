package com.ponsun.san;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
@EnableAsync  // Enables async method execution
public class PONSUN_SAN_Application {
    public static void main(String[] args) {
        SpringApplication.run(PONSUN_SAN_Application.class, args);
    }
}