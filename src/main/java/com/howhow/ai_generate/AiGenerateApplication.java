package com.howhow.ai_generate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class AiGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiGenerateApplication.class, args);
    }

}
