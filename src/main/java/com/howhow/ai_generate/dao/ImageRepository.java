package com.howhow.ai_generate.dao;

import com.howhow.ai_generate.model.document.ImageDocument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<ImageDocument, String> {}
