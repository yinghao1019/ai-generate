package com.howhow.ai_generate.model.open_ai;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
