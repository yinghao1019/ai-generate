package com.howhow.ai_generate.model.document;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Data
@Document(collation = "image")
public class ImageDocument {

    @Id private String id;

    private String userId;

    private String prompt;

    private String imageUrl;

    private OffsetDateTime createdTimestamp;
}
