package com.howhow.ai_generate.client;

import com.howhow.ai_generate.exception.ChatCompletionException;
import com.howhow.ai_generate.model.dto.TextGenerationDTO;
import com.howhow.ai_generate.model.open_ai.ChatCompletionResponse;
import com.howhow.ai_generate.model.open_ai.Choice;
import com.howhow.ai_generate.model.open_ai.Message;

import io.micrometer.common.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OpenAiClient {
    private final WebClient webClient;

    @Value("${openai.key}")
    private String openAiKey;

    public OpenAiClient(WebClient.Builder webClientBuilder) {
        this.webClient =
                webClientBuilder
                        .baseUrl("https://api.openai.com/v1")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiKey)
                        .build();
    }

    // TODO deserializing response to object
    public Message generateText(String prompt, String userInput) throws ChatCompletionException,RestClientException {
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

        ChatCompletionResponse chatCompletionResponse =
                webClient
                        .post()
                        .uri("/chat/completions")
                        .bodyValue(messages)
                        .retrieve()
                        .bodyToMono(ChatCompletionResponse.class)
                        .doOnError(
                                error -> {
                                    throw new RestClientException(error.getMessage(),error);
                                })
                        .block();
        if (chatCompletionResponse.getChoices().isEmpty()) {
            throw new ChatCompletionException("openai gen error.choices is empty");
        }

        Choice choice = chatCompletionResponse.getChoices().get(0);
        edgeCaseException(choice);
        return choice.getMessage();
    }

    private void edgeCaseException(Choice choice) throws ChatCompletionException {
        if ("length".equals(choice.getFinishReason())) {
            throw new ChatCompletionException(
                    String.format("openai gen error.reason is :%s", choice.getFinishReason()));
        }

        if ("content_filter".equals(choice.getFinishReason())) {
            throw new ChatCompletionException(
                    String.format("openai gen error.reason is :%s", choice.getFinishReason()));
        }
        if ("stop".equals(choice.getFinishReason())) {
            throw new ChatCompletionException(
                    String.format("openai gen error.reason is :%s", choice.getFinishReason()));
        }

        if (StringUtils.isNotEmpty(choice.getMessage().getRefusal())) {
            throw new ChatCompletionException(
                    String.format("openai gen error.refusal:%s", choice.getMessage().getRefusal()));
        }
    }
}
