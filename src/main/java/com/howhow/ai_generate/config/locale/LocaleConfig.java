package com.howhow.ai_generate.config.locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleConfig {
    @Bean
    public LocaleResolver localeResolver() {
        // config support Locales，若 Accept-Language 沒設定或不在這清單內就會使用預設的 Locale
        List<Locale> supportedLocales = new ArrayList<>();
        supportedLocales.add(Locale.TAIWAN);
        supportedLocales.add(Locale.ENGLISH);

        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.TAIWAN); // config Locale
        acceptHeaderLocaleResolver.setSupportedLocales(supportedLocales);
        return acceptHeaderLocaleResolver;
    }
}
