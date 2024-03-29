package com.koopey.api.configuration;

import com.koopey.api.ServerApplication;
import com.koopey.api.configuration.properties.CustomProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class CustomPropertiesIntegrationTest {

    @Autowired
    private CustomProperties customProperties;

    @Test
    public void whenCustomPropertiesReadThenReturnsPropertyValues() throws Exception {
        assertThat(customProperties.getGoogleApiKey()).isEqualTo("googleapikey");
        assertThat(customProperties.getRabbitmqExchange()).isEqualTo("koopey.direct");
    }

}