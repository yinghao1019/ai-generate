package com.howhow.ai_generate.config;

import com.howhow.ai_generate.interceptor.MdcWebRequestInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(new MdcWebRequestInterceptor()).addPathPatterns("/**");
    }
}
