package com.koopey.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
      //  registry.addMapping("/**").allowedOrigins("http://127.0.0.1");
                // .allowedOrigins("http://127.0.0.1") .allowCredentials(true)  .allowedHeaders("*").allowedMethods("HEAD", "DELETE", "GET", "OPTIONS", "POST", "PUT")
             //  .allowedOriginPatterns("http://127.0.0.1").allowedOriginPatterns("https://*.hoopey.com").allowCredentials(false)
    }
}