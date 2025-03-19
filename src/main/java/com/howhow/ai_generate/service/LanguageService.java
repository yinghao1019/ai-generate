package com.howhow.ai_generate.service;

import com.howhow.ai_generate.dao.LanguageRepository;
import com.howhow.ai_generate.exception.BadRequestException;
import com.howhow.ai_generate.model.document.LanguageDocument;
import com.howhow.ai_generate.model.dto.LanguageDTO;
import com.howhow.ai_generate.model.dto.LanguageRequestDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<LanguageDTO> getAllLanguage() {
        return languageRepository.findAll().stream()
                .map(
                        languageDocument -> {
                            LanguageDTO languageDTO = new LanguageDTO();
                            languageDTO.setId(languageDocument.getId());
                            languageDTO.setLanguageCode(languageDocument.getLanguageCode());
                            languageDTO.setLanguageName(languageDocument.getLanguageName());

                            return languageDTO;
                        })
                .toList();
    }

    public LanguageDTO createLanguage(LanguageRequestDTO languageRequestDTO) {
        if (languageRepository.existsByLanguageCodeOrLanguageName(
                languageRequestDTO.getLanguageCode(), languageRequestDTO.getLanguageName())) {
            throw new BadRequestException("Language code or name is duplicate");
        }

        LanguageDocument languageDocument = new LanguageDocument();
        languageDocument.setLanguageCode(languageRequestDTO.getLanguageCode());
        languageDocument.setLanguageName(languageRequestDTO.getLanguageName());
        languageRepository.save(languageDocument);

        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setId(languageDocument.getId());
        languageDTO.setLanguageCode(languageDocument.getLanguageCode());
        languageDTO.setLanguageName(languageDocument.getLanguageName());
        return languageDTO;
    }
}
