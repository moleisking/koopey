package com.koopey.api.configuration.properties;

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

    @Value("${spring.mail.username}")
    private String emailAddress = "name@domain.com";

    @Value("${spring.mail.password}")
    private String emailPassword = "12345";

    @Value("${custom.api.google.key}")
    private String googleApiKey = "apikey";

    @Value("${custom.jwt.key}")
    private String jwtKey = "key";

    @Value("${custom.jwt.issuer}")
    private String jwtIssuer = "http://localhost";

    @Value("${custom.file.tags}")
    private String tagsFileName = "filename";

    @Value("${custom.verification.url}")
    private String verificationUrl = "http://localhost:4200";

    @Value("${custom.verification.enable}")
    private Boolean verificationEnable = false;

}
