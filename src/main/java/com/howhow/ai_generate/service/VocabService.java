package com.howhow.ai_generate.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.howhow.ai_generate.client.OpenAiClient;
import com.howhow.ai_generate.dao.VocabRepository;
import com.howhow.ai_generate.exception.BadRequestException;
import com.howhow.ai_generate.model.document.VocabDocument;
import com.howhow.ai_generate.model.dto.GenVocabRequestDTO;
import com.howhow.ai_generate.model.dto.GenVocabResponseDTO;
import com.howhow.ai_generate.model.open_ai.Message;
import com.howhow.ai_generate.utils.JsonUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabService {
    private final VocabRepository vocabRepository;

    private final OpenAiClient openAIClient;

    private final JsonParser jsonParser = new JsonParser();

    public GenVocabResponseDTO generateVocab(GenVocabRequestDTO requestDTO) {
        if (vocabRepository.existsById(requestDTO.getLanguage())) {
            throw new BadRequestException("Vocab already exists");
        }

        OffsetDateTime now = OffsetDateTime.now();
        // TODO 檢查語言
        Message message =
                openAIClient.generateText(
                        """
                    請作為一個單字聯想，根據所提供的主題聯想5個相關單字，與五個對應的繁體中文翻譯。
                    #輸入範例:
                        主題: 水果,
                        語言: English
                    #輸出Json 範例:{
                                    wordList: ["Apple","Banana","Cherry","Date","Elderberry"],
                                    zhWordList: ["蘋果","香蕉","櫻桃","棗子","接骨木"]
                                    }
            """,
                        requestDTO.getUserInput());
        JsonObject data = jsonParser.parse(message.getContent()).getAsJsonObject();
        List<String> wordList = JsonUtils.getStringList(data, "wordList");
        List<String> zhWordList = JsonUtils.getStringList(data, "zhWordList");
        // 存入資料庫
        VocabDocument vocabDocument = new VocabDocument();
        vocabDocument.setTitle(requestDTO.getUserInput());
        vocabDocument.setLanguageId(requestDTO.getLanguage());
        vocabDocument.setWordList(wordList);
        vocabDocument.setZhWordList(zhWordList);
        vocabDocument.setCreatedTimestamp(now);

        vocabRepository.save(vocabDocument);

        GenVocabResponseDTO responseDTO = new GenVocabResponseDTO();
        responseDTO.setTitle(requestDTO.getUserInput());
        responseDTO.setWordList(wordList);
        responseDTO.setZhWordList(zhWordList);
        responseDTO.setLanguage(requestDTO.getLanguage());
        responseDTO.setCreatedAt(now.toEpochSecond());
        return responseDTO;
    }
}
