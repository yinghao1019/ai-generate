package com.howhow.ai_generate.model.dto;

import lombok.Data;

@Data
public class GenImageResponseDTO {
    private String userInput;
    private String imageUrl;
    private Long createdAt;
}
