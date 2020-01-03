package com.koopey.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {        
        registry.addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowCredentials(true);
    }
}