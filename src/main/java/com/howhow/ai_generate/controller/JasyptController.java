package com.howhow.ai_generate.controller;

import com.howhow.ai_generate.model.dto.DataDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Jasypt", description = "Jasypt 加解密")
@RestController
@RequiredArgsConstructor
@Profile("!prod")
public class JasyptController {
    private final StringEncryptor encryptor;

    @Operation(
            operationId = "jasypt-encrypt",
            summary = "jasypt 加密",
            tags = {"Jasypt"})
    @PostMapping("/encrypt")
    public ResponseEntity<DataDTO> encryptString(
            @Valid
                    @Parameter(name = "encrypt data", description = "待加密資料", required = true)
                    @NotNull
                    @RequestBody
                    String painText) {
        return ResponseEntity.ok().body(new DataDTO().data(encryptor.encrypt(painText)));
    }

    @Operation(
            operationId = "jasypt-encrypt",
            summary = "jasypt 解密",
            tags = {"Jasypt"})
    @PostMapping(value = "/decrypt")
    public ResponseEntity<DataDTO> decryptString(
            @Valid
                    @Parameter(name = "decrypt data", description = "待解密資料", required = true)
                    @NotNull
                    @RequestBody
                    String stringToDecrypt) {
        return ResponseEntity.ok().body(new DataDTO().data(encryptor.decrypt(stringToDecrypt)));
    }
}
