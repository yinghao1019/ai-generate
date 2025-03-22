package com.howhow.ai_generate.controller;

import com.howhow.ai_generate.model.dto.GenImageRequestDTO;
import com.howhow.ai_generate.model.dto.GenImageResponseDTO;
import com.howhow.ai_generate.service.ImageService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Image", description = "單字產生")
@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    // TODO 驗證身分權限
    @PostMapping("/generate")
    public GenImageResponseDTO generateImage(@Validated GenImageRequestDTO requestDTO) {
        return imageService.generateImage(requestDTO.getUserInput());
    }
}
