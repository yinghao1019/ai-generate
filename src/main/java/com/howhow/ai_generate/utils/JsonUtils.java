package com.howhow.ai_generate.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> getStringList(JsonNode obj, String fieldName) {
        List<String> result = new ArrayList<>();
        JsonNode fieldValue = obj.get(fieldName);
        if (fieldValue.isNull() || !fieldValue.isArray()) {
            return result;
        }

        for (JsonNode value : fieldValue) {
            result.add(value.textValue());
        }
        return result;
    }
}
