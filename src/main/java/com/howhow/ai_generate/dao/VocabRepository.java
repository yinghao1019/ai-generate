package com.howhow.ai_generate.dao;

import com.howhow.ai_generate.model.document.VocabDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabRepository extends MongoRepository<VocabDocument, String> {}
