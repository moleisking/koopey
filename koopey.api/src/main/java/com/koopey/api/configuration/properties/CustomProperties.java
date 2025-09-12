package com.koopey.api.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
public class CustomProperties {

    @Value("${custom.image.avatar.max:131072}")
    private Integer avatarMaxSize = 131072;

    @Value("${spring.mail.username:name@domain.com}")
    private String emailAddress = "name@domain.com";

    @Value("${spring.mail.password:Copy string literal text to the clipboard}")
    private String emailPassword = "12345";

    @Value("${custom.api.google.key:apikey}")
    private String googleApiKey = "apikey";

    @Value("${custom.jwt.expire:267840}")
    private Long jwtExpire = (long) 267840;

    @Value("${custom.jwt.key:key}")
    private String jwtKey = "key";

    @Value("${custom.jwt.issuer:http://localhost}")
    private String jwtIssuer = "http://localhost";

    @Value("${custom.kafka.group:groupid}")
    private String kafkaGroup = "groupid";

    @Value("${custom.kafka.port:9092}")
    private Integer kafkaPort = 9092;

    @Value("${custom.kafka.server:localhost}")
    private String kafkaServer = "localhost";

    @Value("${custom.file.tags:filename}")
    private String tagsFileName = "filename";

    @Value("${custom.api.microsoft.key:apikey}")
    private String microsoftApiKey = "apikey";

    @Value("${custom.verification.url:http://localhost:4200/authenticate/}")
    private String verificationUrl = "http://localhost:4200/authenticate/";

    @Value("${custom.verification.enable:false}")
    private Boolean verificationEnable = false;

    @Value("${custom.rabbitmq.host:127.0.0.1}")
    private String rabbitmqHost = "127.0.0.1";

    @Value("${custom.rabbitmq.port:15672}")
    private Integer rabbitmqPort = 15672;

    @Value("${custom.rabbitmq.user:koopey}")
    private String rabbitmqUser = "koopey";

    @Value("${custom.rabbitmq.password:password}")
    private String rabbitmqPassword = "password";

    @Value("${custom.rabbitmq.exchange:koopey}")
    private String rabbitmqExchange = "koopey";

    @Value("${custom.rabbitmq.enable:false}")
    private Boolean rabbitmqEnable = false;

    @Value("${custom.rabbitmq.routekey:routeKey}")
    private String rabbitmqRouteKey = "routeKey";

    @Value("${custom.rabbitmq.queue:koopey}")
    private String rabbitmqQueue = "koopey";

    @Value("${custom.keycloak.enable:false}")
    private Boolean keycloakEnable = false;

    @Value("${custom.keycloak.port:1710}")
    private Integer keycloakPort = 1710;

}
