package com.howhow.ai_generate.model.open_ai;

import com.openai.core.JsonValue;
import com.openai.models.ResponseFormatJsonSchema;

public class JsonResponseFormat {
    private final String type;
    private final Object required;
    private final Object properties;

    public JsonResponseFormat(String type, Object required, Object properties) {
        this.type = type;
        this.required = required;
        this.properties = properties;
    }

    public ResponseFormatJsonSchema toResponseFormat() {

        ResponseFormatJsonSchema.JsonSchema.Schema schema =
                ResponseFormatJsonSchema.JsonSchema.Schema.builder()
                        .putAdditionalProperty("type", JsonValue.from(type))
                        .putAdditionalProperty("properties", JsonValue.from(properties))
                        .putAdditionalProperty("required", JsonValue.from(required))
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
}
