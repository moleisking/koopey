package com.koopey.api.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
public class CustomProperties {
    
    @Value("${custom.image.avatar.max}")
    private Integer avatarMaxSize = 131072;

    @Value("${custom.api.google.key}")
    private String googleApiKey = "apikey";

    @Value("${custom.file.tags}")
    private String tagsFileName = "filename";

}
