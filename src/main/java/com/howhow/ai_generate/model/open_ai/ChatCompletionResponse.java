package com.howhow.ai_generate.model.open_ai;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

    @SerializedName("service_tier")
    private String serviceTier;
}
