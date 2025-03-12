package com.howhow.ai_generate.config;

import com.howhow.ai_generate.model.converter.DateToOffsetDatetimeConverter;
import com.howhow.ai_generate.model.converter.OffsetDatetimeToDateConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                Arrays.asList(
                        new DateToOffsetDatetimeConverter(), new OffsetDatetimeToDateConverter()));
    }
}
