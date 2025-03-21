package com.example.crmachievement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CrmAchievementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmAchievementApplication.class, args);
        log.info("---------------------------------------------");
        log.info("Local: {}", "http://localhost:8080");
        log.info("Swagger: {}", "http://localhost:8080/doc.html");
        log.info("---------------------------------------------");
    }

}
