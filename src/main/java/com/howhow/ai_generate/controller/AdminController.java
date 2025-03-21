package com.howhow.ai_generate.controller;

import com.howhow.ai_generate.model.dto.LanguageDTO;
import com.howhow.ai_generate.model.dto.LanguageRequestDTO;
import com.howhow.ai_generate.service.LanguageService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "後台功能")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LanguageService languageService;

    //    TODO authentication and authorization
    @PostMapping("/languages")
    public LanguageDTO createLanguage(@RequestBody @Validated LanguageRequestDTO requestDTO) {
        return languageService.createLanguage(requestDTO);
    }
}
