package com.howhow.ai_generate.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenVocabResponseDTO {
    private String title;
    private List<String> wordList;
    private List<String> zhWordList;
    private String language;
    private Long createdAt;
}
