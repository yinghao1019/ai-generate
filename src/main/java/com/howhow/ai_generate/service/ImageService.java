package com.howhow.ai_generate.service;

import com.howhow.ai_generate.dao.ImageRepository;
import com.howhow.ai_generate.manager.OpenAIManager;
import com.howhow.ai_generate.model.document.ImageDocument;
import com.howhow.ai_generate.model.dto.GenImageResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final OpenAIManager openAIManager;

    private final ImageRepository imageRepository;

    public GenImageResponseDTO generateImage(String userInput) {
        OffsetDateTime current = OffsetDateTime.now();
        String url = openAIManager.generateImage(userInput);
        // TODO upload image to azure blob storage
        // TODO save image author id
        ImageDocument imageDocument = new ImageDocument();
        imageDocument.setPrompt(userInput);
        imageDocument.setImageUrl(url);
        imageDocument.setCreatedTimestamp(current);
        imageRepository.save(imageDocument);

        GenImageResponseDTO genImageResponseDTO = new GenImageResponseDTO();
        genImageResponseDTO.setUserInput(userInput);
        genImageResponseDTO.setImageUrl(url);
        genImageResponseDTO.setCreatedAt(current.toEpochSecond());
        return genImageResponseDTO;
    }
}
