package com.koopey.service.configuration;

import com.koopey.api.ServerApplication;
import com.koopey.api.configuration.properties.CustomProperties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;


import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@ExtendWith({SpringRunner.class})
@SpringBootTest(classes = ServerApplication.class)
//@TestPropertySource("classpath:application-test.properties")
public class CustomPropertiesIntegrationTest {

    @Autowired
    private CustomProperties customProperties;

    @Test
    public void whenCustomPropertiesReadThenReturnsPropertyValues() throws Exception {
        assertThat( customProperties.getGoogleApiKey()).isEqualTo("googleapikey");    
    }

}