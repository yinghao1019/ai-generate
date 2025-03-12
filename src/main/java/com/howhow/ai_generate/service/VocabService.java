package com.howhow.ai_generate.service;

import com.howhow.ai_generate.dao.VocabRepository;
import com.howhow.ai_generate.model.dto.GenVocabRequestDTO;
import com.howhow.ai_generate.model.dto.GenVocabResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VocabService {
    private final VocabRepository vocabRepository;

    public GenVocabResponseDTO generateVocab(GenVocabRequestDTO requestDTO) {
        // TODO
        // 檢查語言是否支援
        // 串接open ai 產生文字API
        // 存入資料庫

        return null;
    }
}
