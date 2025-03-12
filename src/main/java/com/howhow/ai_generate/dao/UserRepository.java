package com.howhow.ai_generate.dao;

import com.howhow.ai_generate.model.document.UserDocument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {}
