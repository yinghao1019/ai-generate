package com.howhow.ai_generate.model.document;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "language")
public class LanguageDocument {
    @Id private String id;
    private String languageCode;
    private String languageName;
}
