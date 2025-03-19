package com.howhow.ai_generate.model.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import lombok.Data;

import java.util.List;

@Data
public class TextGenerationDTO {
    private final JsonObject responseFormat;
    private String model;
    private List<TextContentDTO> messages;

    public TextGenerationDTO() {
        this.responseFormat = new JsonObject();
        responseFormat.addProperty("type", "json_schema");

        JsonObject jsonSchema = new JsonObject();
        jsonSchema.addProperty("name", "generated_text");
        // Create the schema object
        JsonObject schema = new JsonObject();
        schema.addProperty("type", "object");

        JsonObject items = new JsonObject();
        items.addProperty("type", "string");
        JsonObject wordList = new JsonObject();
        wordList.addProperty("type", "array");
        wordList.add("items", items);
        // zh wordList
        JsonObject zhWordList = new JsonObject();
        wordList.addProperty("type", "array");
        wordList.add("items", items);
        // Define properties
        JsonObject properties = new JsonObject();
        properties.add("wordList", wordList);
        properties.add("zhWordList", zhWordList);

        schema.add("properties", properties);

        // Required fields
        JsonArray required = new JsonArray();
        required.add(new JsonPrimitive("wordList"));
        required.add(new JsonPrimitive("zhWordList"));
        schema.add("required", required);

        // Additional properties and strict
        schema.addProperty("additionalProperties", false);
        jsonSchema.add("schema", schema);
        jsonSchema.addProperty("strict", true);
    }

    @Data
    public static class TextContentDTO {
        private String role;
        private String content;
    }
}
