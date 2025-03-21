package com.howhow.ai_generate.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howhow.ai_generate.exception.ChatCompletionException;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.ChatModel;
import com.openai.models.ResponseFormatJsonSchema;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Component
public class OpenAIManager {
    private OpenAIClient client;

    public OpenAIManager(OpenAIOkHttpClient.Builder aiClientBuilder) {
        client = aiClientBuilder.build();
    }

    public Optional<String> generateText(String prompt, String userInput)
            throws ChatCompletionException, RestClientException, JsonProcessingException {

        ChatCompletionCreateParams completionCreateParams =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_4O_MINI)
                        .addSystemMessage(prompt)
                        .addUserMessage(userInput)
                        .responseFormat(createJsonSchemaFormat())
                        .build();
        ChatCompletion chatCompletion = client.chat().completions().create(completionCreateParams);
        List<ChatCompletion.Choice> choices = chatCompletion.choices();
        Optional<String> content = choices.get(0).message().content();

        return content;
    }

    private ResponseFormatJsonSchema createJsonSchemaFormat() throws JsonProcessingException {

        JsonNode propertiesValue =
                new ObjectMapper()
                        .readTree(
                                """
{
    "wordList": {
        "type": "array",
        "items": {
            "type": "string"
        }
    },
    "zhWordList": {
        "type": "array",
        "items": {
            "type": "string"
        }
    }
}
""");

        ResponseFormatJsonSchema.JsonSchema.Schema schema =
                ResponseFormatJsonSchema.JsonSchema.Schema.builder()
                        .putAdditionalProperty("type", JsonValue.from("object"))
                        .putAdditionalProperty(
                                "properties", JsonValue.fromJsonNode(propertiesValue))
                        .putAdditionalProperty(
                                "required", JsonValue.from(List.of("wordList", "zhWordList")))
                        .putAdditionalProperty("additionalProperties", JsonValue.from(false))
                        .build();
        return ResponseFormatJsonSchema.builder()
                .jsonSchema(
                        ResponseFormatJsonSchema.JsonSchema.builder()
                                .name("word_lists")
                                .schema(schema)
                                .strict(true)
                                .build())
                .build();
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
