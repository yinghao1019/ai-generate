package com.howhow.ai_generate.model.open_ai;

import lombok.Data;

@Data
public class JsonArrayProperty {

    private final String type = "array";
    private final Object items;

    public JsonArrayProperty(Object items) {
        this.items = items;
    }
}
