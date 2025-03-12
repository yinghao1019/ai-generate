package com.howhow.ai_generate.config;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaticApplicationContext {
    private static StaticApplicationContext instance;
    private final ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }
}
