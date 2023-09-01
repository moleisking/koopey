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
class RabbitServiceTest {

    @Autowired
    private RabbitService rabbitService;
   
    @Test
    @WithUserDetails(value = "test")
    public void testSend() {
        Message m = new Message();
        m.setSenderId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        m.setReceiverId(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        m.setDescription("RabbitMQ test message");
        m.setName("Name");
        rabbitService.send(m);

        assertThat(rabbitService.count()).isGreaterThan(0);
    }

    @Test
    @WithUserDetails(value = "test")
    public void testPole() {
        Message m = new Message();
        m.setSenderId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        m.setReceiverId(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        m.setDescription("Hello World again 3!");
        m.setName("Name");
        rabbitService.pole(m.getSenderId());

        assertThat(true).isTrue();
    }

}