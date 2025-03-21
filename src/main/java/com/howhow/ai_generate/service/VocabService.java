package com.howhow.ai_generate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howhow.ai_generate.dao.LanguageRepository;
import com.howhow.ai_generate.dao.VocabRepository;
import com.howhow.ai_generate.exception.BadRequestException;
import com.howhow.ai_generate.manager.OpenAIManager;
import com.howhow.ai_generate.model.document.LanguageDocument;
import com.howhow.ai_generate.model.document.VocabDocument;
import com.howhow.ai_generate.model.dto.GenVocabRequestDTO;
import com.howhow.ai_generate.model.dto.GenVocabResponseDTO;
import com.howhow.ai_generate.model.open_ai.JsonArrayProperty;
import com.howhow.ai_generate.model.open_ai.JsonResponseFormat;
import com.howhow.ai_generate.model.open_ai.JsonStringObject;
import com.howhow.ai_generate.utils.JsonUtils;
import com.openai.models.ResponseFormatJsonSchema;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VocabService {
    private final VocabRepository vocabRepository;
    private final LanguageRepository languageRepository;

    private final OpenAIManager openAIManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GenVocabResponseDTO generateVocab(GenVocabRequestDTO requestDTO)
            throws BadRequestException, JsonProcessingException {
        LanguageDocument language =
                languageRepository
                        .findById(requestDTO.getLanguageId())
                        .orElseThrow(() -> new BadRequestException("Language not found"));

        OffsetDateTime now = OffsetDateTime.now();
        String message =
                """
                    請作為一個單字聯想，根據所提供的主題聯想5個相關單字，與五個對應的繁體中文翻譯。
                    #輸入範例:
                        主題: 水果,
                        語言: English
                    #輸出Json 範例:{
                                    wordList: ["Apple","Banana","Cherry","Date","Elderberry"],
                                    zhWordList: ["蘋果","香蕉","櫻桃","棗子","接骨木"]
                                    }
            """;

        String completionContent =
                openAIManager.generateText(
                        message, requestDTO.getUserInput(), createGenVocabFormat());
        JsonNode data = objectMapper.readTree(completionContent);
        List<String> wordList = JsonUtils.getStringList(data, "wordList");
        List<String> zhWordList = JsonUtils.getStringList(data, "zhWordList");
        // save to db
        VocabDocument vocabDocument = new VocabDocument();
        vocabDocument.setTitle(requestDTO.getUserInput());
        vocabDocument.setLanguageId(requestDTO.getLanguageId());
        vocabDocument.setWordList(wordList);
        vocabDocument.setZhWordList(zhWordList);
        vocabDocument.setCreatedTimestamp(now);
        vocabRepository.save(vocabDocument);

        // create response
        GenVocabResponseDTO responseDTO = new GenVocabResponseDTO();
        responseDTO.setTitle(requestDTO.getUserInput());
        responseDTO.setWordList(wordList);
        responseDTO.setZhWordList(zhWordList);
        responseDTO.setLanguage(language.getLanguageName());
        responseDTO.setCreatedAt(now.toEpochSecond());
        return responseDTO;
    }

    private ResponseFormatJsonSchema createGenVocabFormat() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("wordList", new JsonArrayProperty(new JsonStringObject()));
        properties.put("zhWordList", new JsonArrayProperty(new JsonStringObject()));
        return new JsonResponseFormat("object", List.of("wordList", "zhWordList"), properties)
                .toResponseFormat();
    }
}
