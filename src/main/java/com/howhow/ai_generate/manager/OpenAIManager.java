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
import com.openai.models.images.Image;
import com.openai.models.images.ImageGenerateParams;
import com.openai.models.images.ImageModel;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

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

    public String generateImage(String userInput) {
        ImageGenerateParams imageGenerateParams =
                ImageGenerateParams.builder()
                        .model(ImageModel.DALL_E_3)
                        .prompt(userInput)
                        .size(ImageGenerateParams.Size._1024X1024)
                        .n(1)
                        .build();
        List<Image> images = client.images().generate(imageGenerateParams).data();
        if (images.isEmpty()) {
            throw new OpenAIInvalidDataException("openai gen error.images is empty");
        }

        Optional<String> url = images.get(0).url();
        return url.orElseThrow(
                () -> new OpenAIInvalidDataException("openai gen error.url is empty"));
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
