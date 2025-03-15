package com.howhow.ai_generate.model.open_ai;

import lombok.Data;

@Data
public class Message {
    private String role;
    private String content;
}
