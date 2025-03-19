package com.howhow.ai_generate.controller;

import com.howhow.ai_generate.model.dto.GenVocabRequestDTO;
import com.howhow.ai_generate.model.dto.GenVocabResponseDTO;
import com.howhow.ai_generate.service.VocabService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Vocab", description = "單字產生")
@RequiredArgsConstructor
@RestController
@RequestMapping("/vocab")
public class VocabController {
    private final VocabService vocabService;

    // TODO 驗證身分權限
    @PostMapping("/generate")
    public GenVocabResponseDTO generateVocab(
            @RequestBody @Validated GenVocabRequestDTO requestDTO) {
        return vocabService.generateVocab(requestDTO);
    }
}
