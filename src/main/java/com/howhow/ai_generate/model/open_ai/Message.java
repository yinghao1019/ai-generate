package com.howhow.ai_generate.model.open_ai;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private String role;
    private String content;
    // Nullable
    private String refusal;
    private List<Object> annotations;
}
