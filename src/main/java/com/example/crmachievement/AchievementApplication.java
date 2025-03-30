package com.example.crmachievement;

import com.example.crmachievement.common.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.example.crmachievement"})
public class AchievementApplication {
    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }
    public static void main(String[] args) {
        SpringApplication.run(AchievementApplication.class, args);
        log.info("---------------------------------------------");
        log.info("Local: {}", "http://localhost:8080");
        log.info("Swagger: {}", "http://localhost:8080/doc.html");
        log.info("---------------------------------------------");
    }

}
