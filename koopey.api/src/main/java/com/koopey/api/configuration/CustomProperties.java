package com.koopey.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {
    
    public Integer avatarMaxSize = 131072;
    public String googleApiKey = "aaa";
    public String xxx = "aaa";
}

