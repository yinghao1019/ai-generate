package com.howhow.ai_generate.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> getStringList(JsonObject obj, String fieldName) {
        List<String> result = new ArrayList<>();
        JsonElement fieldValue = obj.get(fieldName);
        if (fieldValue.isJsonNull()) {
            return result;
        }

        for (JsonElement element : fieldValue.getAsJsonArray()) {
            result.add(element.getAsString());
        }
        return result;
    }
}
