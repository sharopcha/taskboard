package com.taskboard.taskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.taskboard")
public class TaskboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskboardApplication.class, args);
    }

}
