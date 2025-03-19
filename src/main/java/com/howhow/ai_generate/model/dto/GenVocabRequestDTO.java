package com.howhow.ai_generate.model.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GenVocabRequestDTO {
    @NotNull private String userInput;
    @NotNull private String languageId;
}
