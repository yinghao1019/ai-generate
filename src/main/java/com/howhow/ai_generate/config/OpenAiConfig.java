package com.howhow.ai_generate.config;

import com.openai.client.okhttp.OpenAIOkHttpClient;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenAiConfig {
    @Value("${openai.key}")
    private String openAiKey;

    @Bean
    public OpenAIOkHttpClient.Builder aiClientBuilder() {
        return OpenAIOkHttpClient.builder().apiKey(openAiKey);
    }
}
