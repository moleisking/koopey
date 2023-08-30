package com.koopey.api.configuration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.FilterChainProxy;
//import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConfiguration
//@WebAppConfiguration
public class WebClientTestConfigurationTest {

  
    
    //@Autowired
    //private FilterChainProxy springSecurityFilterChain;

    /*@BeforeAll
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }*/

     /*    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
          .apply(springSecurity())
          .build();
    }*/

   /*  @Bean
    public WebClient getWebClient(final WebClient.Builder builder) {

        WebClient webClient = builder
                .baseUrl("http://localhost")
                .build();

        return webClient;
    }*/
}
