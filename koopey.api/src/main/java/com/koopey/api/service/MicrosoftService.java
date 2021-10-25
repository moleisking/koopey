package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.parser.GoogleParser;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service 
public class MicrosoftService {
    
    @Autowired
    private CustomProperties customProperties;    

    @PostConstruct
    private void init() {
        log.info("MICROSOFT_API_KEY: {}", System.getenv("MICRSOFT_API_KEY"));
        log.info("MicrosoftApiKey: {}", customProperties.getMicrosoftApiKey());
    }
}
