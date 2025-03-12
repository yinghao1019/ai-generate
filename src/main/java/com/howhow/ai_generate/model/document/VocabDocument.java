package com.howhow.ai_generate.model.document;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Document(collation = "vocab")
public class VocabDocument {
    @Id private String id;

    private String title;

    private String language;

    private List<String> wordList;

    private List<String> zhWordList;

    private OffsetDateTime createdTimestamp;

    private String userId;
}
