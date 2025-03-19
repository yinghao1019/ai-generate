package com.howhow.ai_generate.model.open_ai;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Usage {
    @SerializedName("prompt_tokens")
    private int promptTokens;

    @SerializedName("completion_tokens")
    private int completionTokens;

    @SerializedName("total_tokens")
    private int totalTokens;

    @SerializedName("prompt_tokens_details")
    private PromptTokensDetails promptTokensDetails;

    @SerializedName("completion_tokens_details")
    private CompletionTokensDetails completionTokensDetails;

    @Data
    public static class PromptTokensDetails {
        @SerializedName("cached_tokens")
        private int cachedTokens;

        @SerializedName("audio_tokens")
        private int audioTokens;
    }

    @Data
    public static class CompletionTokensDetails {
        @SerializedName("reasoning_tokens")
        private int reasoningTokens;

        @SerializedName("audio_tokens")
        private int audioTokens;

        @SerializedName("accepted_prediction_tokens")
        private int acceptedPredictionTokens;

        @SerializedName("rejected_prediction_tokens")
        private int rejectedPredictionTokens;
    }
}
