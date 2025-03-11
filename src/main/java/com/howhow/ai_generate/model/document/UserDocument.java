package com.howhow.ai_generate.model.document;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Data
@Document(collation = "user")
public class UserDocument {
    @Id private String id;
    private String name;
    private String email;

    @Indexed(unique = true)
    private String provider;

    @Indexed(unique = true)
    private String providerId;

    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;
}
