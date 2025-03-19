package com.howhow.ai_generate.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class DataDTO {

    private String data;

    public DataDTO data(String data) {
        this.data = data;
        return this;
    }
}
