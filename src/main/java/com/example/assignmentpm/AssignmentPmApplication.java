package com.example.assignmentpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;


@EnableRetry
@SpringBootApplication
public class AssignmentPmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentPmApplication.class, args);
    }

}
