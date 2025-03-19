package com.howhow.ai_generate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition
@Configuration
@Profile("!prod")
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        // 定義全局參數
        Parameter acceptLanguageParam =
                new Parameter()
                        .name("Accept-Language")
                        .description("英文：en")
                        .in("header") // 指定為標頭參數
                        .required(false)
                        .schema(new io.swagger.v3.oas.models.media.StringSchema());

        return new OpenAPI()

                .info(apiInfo())
                .components(
                        new Components()
                                .addParameters(
                                        "acceptLanguage", acceptLanguageParam)); // 添加到 components
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public") // 分組名稱
                .packagesToScan("com.howhow.ai_generate.controller") // 掃描的包
                .pathsToMatch("/**") // 匹配所有路徑
                .pathsToExclude("/error/**") // 排除錯誤路徑
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title("Open AI Web Application")
                .description("This is Open Ai practice")
                .contact(new Contact().name("Howard Hung").email("z112517z@gmail.com"));
    }
}
