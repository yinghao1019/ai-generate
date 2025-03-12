package com.howhow.ai_generate.utils;

import com.howhow.ai_generate.config.StaticApplicationContext;
import com.howhow.ai_generate.exception.InternalServerErrorException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocaleUtils {
    private static final MessageSource messageSource;

    static {
        messageSource = StaticApplicationContext.getBean(MessageSource.class);
    }

    public static String get(String msgKey) {
        return LocaleUtils.get(msgKey, (Object) null);
    }

    public static String get(String msgKey, Object... args) {
        try {
            return messageSource.getMessage(msgKey, args, getLocale());
        } catch (Exception e) {
            throw new InternalServerErrorException("翻譯失敗:" + msgKey + ", " + e.getMessage());
        }
    }

    private static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
