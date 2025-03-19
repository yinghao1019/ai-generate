package com.howhow.ai_generate.model.open_ai;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Choice {
    private int index;
    private Message message;
    // Nullable, can be complex in other cases
    private Object logprobs;

    @SerializedName("finish_reason")
    private String finishReason;
}
