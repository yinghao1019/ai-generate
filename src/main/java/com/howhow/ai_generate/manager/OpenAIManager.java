package com.howhow.ai_generate.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.howhow.ai_generate.exception.ChatCompletionException;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.errors.OpenAIInvalidDataException;
import com.openai.models.ChatModel;
import com.openai.models.ResponseFormatJsonSchema;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class OpenAIManager {
    private final OpenAIClient client;

    public OpenAIManager(OpenAIOkHttpClient.Builder aiClientBuilder) {
        client = aiClientBuilder.build();
    }

    public String generateText(
            String prompt, String userInput, ResponseFormatJsonSchema responseFormat)
            throws ChatCompletionException, RestClientException, JsonProcessingException {

        ChatCompletionCreateParams completionCreateParams =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_4O_MINI)
                        .addSystemMessage(prompt)
                        .addUserMessage(userInput)
                        .responseFormat(responseFormat)
                        .build();
        ChatCompletion chatCompletion = client.chat().completions().create(completionCreateParams);
        List<ChatCompletion.Choice> choices = chatCompletion.choices();

        if (choices.isEmpty()) {
            throw new OpenAIInvalidDataException("openai gen error.choices is empty");
        }

        return choices.get(0)
                .message()
                .content()
                .orElseThrow(
                        () -> new OpenAIInvalidDataException("openai gen error.content is empty"));
    }



    //    private void edgeCaseException(Choice choice) throws ChatCompletionException {
    //        if ("length".equals(choice.getFinishReason())) {
    //            throw new ChatCompletionException(
    //                    String.format("openai gen error.reason is :%s",
    // choice.getFinishReason()));
    //        }
    //
    //        if ("content_filter".equals(choice.getFinishReason())) {
    //            throw new ChatCompletionException(
    //                    String.format("openai gen error.reason is :%s",
    // choice.getFinishReason()));
    //        }
    //        if ("stop".equals(choice.getFinishReason())) {
    //            throw new ChatCompletionException(
    //                    String.format("openai gen error.reason is :%s",
    // choice.getFinishReason()));
    //        }
    //
    //        if (StringUtils.isNotEmpty(choice.getMessage().getRefusal())) {
    //            throw new ChatCompletionException(
    //                    String.format("openai gen error.refusal:%s",
    // choice.getMessage().getRefusal()));
    //        }
    //    }
}
