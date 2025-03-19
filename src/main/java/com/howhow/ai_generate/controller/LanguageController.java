package com.howhow.ai_generate.controller;

import com.howhow.ai_generate.model.dto.LanguageDTO;
import com.howhow.ai_generate.service.LanguageService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Language", description = "文字產生")
@RequiredArgsConstructor
@RestController
@RequestMapping("/languages")
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping("")
    public List<LanguageDTO> getLanguageList() {
        return languageService.getAllLanguage();
    }
}
