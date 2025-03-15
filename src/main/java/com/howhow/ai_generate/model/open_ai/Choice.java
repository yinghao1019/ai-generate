package com.howhow.ai_generate.model.open_ai;

import lombok.Data;

@Data
public class Choice {
    private int index;
    private Message message;
    private String finish_reason;
}
