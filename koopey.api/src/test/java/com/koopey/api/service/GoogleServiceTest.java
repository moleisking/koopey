package com.koopey.api.service;

import com.koopey.api.ServerApplication;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.entity.User;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class GoogleServiceTest {
    
    @Autowired
    private GoogleService googleService;
   
    @Test
    @WithUserDetails(value = "test")
    public void getLocation() {}
}
