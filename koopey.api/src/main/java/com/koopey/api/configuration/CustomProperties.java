package com.koopey.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {
    
    public Integer avatarMaxSize;
}
