package com.howhow.ai_generate.constant;

import java.util.Locale;
import java.util.Objects;

public enum LanguageCode {
    /** 英文 */
    EN("en"),
    /** 中文(台灣) */
    ZH_TW("zh-TW");

    private final String value;

    LanguageCode(String value) {
        this.value = value;
    }

    public static LanguageCode fromValue(String value) {
        LanguageCode result = null;
        if (Objects.nonNull(value)) {
            for (LanguageCode languageCode : LanguageCode.values()) {
                if (languageCode.getValue().equals(value)) {
                    result = languageCode;
                    break;
                }
            }
        }
        return result;
    }

    public static LanguageCode fromLocale(Locale locale) {
        return LanguageCode.fromValue(locale.toLanguageTag());
    }

    public String getValue() {
        return value;
    }
}
