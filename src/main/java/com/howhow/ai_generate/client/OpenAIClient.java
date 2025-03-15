package com.howhow.ai_generate.client;

import com.google.gson.Gson;
import com.howhow.ai_generate.model.dto.TextGenerationDTO;
import com.howhow.ai_generate.model.open_ai.Message;
import com.howhow.ai_generate.model.open_ai.OpenAIResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OpenAIClient {
    private final WebClient webClient;
    private final Gson gson = new Gson();

    @Value("${openai.key}")
    private String openAiKey;

    public OpenAIClient(WebClient.Builder webClientBuilder) {
        this.webClient =
                webClientBuilder
                        .baseUrl("https://api.openai.com/v1")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiKey)
                        .build();
    }

    // TODO deserializing response to object
    public Message generateText(String prompt, String userInput) {
        List<TextGenerationDTO.TextContentDTO> messages = new ArrayList<>();
        TextGenerationDTO.TextContentDTO systemContentDTO = new TextGenerationDTO.TextContentDTO();
        systemContentDTO.setContent(prompt);
        systemContentDTO.setRole("system");
        messages.add(systemContentDTO);

        TextGenerationDTO.TextContentDTO userContentDTO = new TextGenerationDTO.TextContentDTO();
        userContentDTO.setContent(userInput);
        userContentDTO.setRole("user");
        messages.add(userContentDTO);

        TextGenerationDTO textGenerationDTO = new TextGenerationDTO();
        textGenerationDTO.setModel("gpt-4o-mini");
        textGenerationDTO.setMessages(messages);

        OpenAIResponse openAIResponse =
                webClient
                        .post()
                        .uri("/chat/completions")
                        .bodyValue(messages)
                        .retrieve()
                        .bodyToMono(OpenAIResponse.class)
                        .doOnError(
                                error -> {
                                    log.error("Error in generating text", error);
                                })
                        .block();

        return openAIResponse.getChoices().get(0).getMessage();
    }
}
