package com.howhow.ai_generate.dao;

import com.howhow.ai_generate.model.document.LanguageDocument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LanguageRepository extends MongoRepository<LanguageDocument, String> {
    boolean existsById(String id);

    boolean existsByLanguageCodeOrLanguageName(String languageCode, String languageName);
}
