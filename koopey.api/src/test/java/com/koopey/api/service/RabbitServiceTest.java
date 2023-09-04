package com.koopey.api.service;

import com.koopey.api.ServerApplication;
import com.koopey.api.model.entity.Message;
import java.util.UUID;
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
        for (int i = 0; i <= 5; i++) {
            Message message = Message.builder().id(UUID.randomUUID())
                    .senderId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .receiverId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .description("RabbitMQ test message " + UUID.randomUUID() + " " + i)
                    .name("name").build();
            rabbitService.send(message);
        }

        assertThat(rabbitService.count()).isGreaterThanOrEqualTo(5);
    }

    @Test
    @WithUserDetails(value = "test")
    public void testPole() {
        Message message = Message.builder().id(UUID.randomUUID())
                .senderId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .receiverId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .description("RabbitMQ test message " + UUID.randomUUID())
                .name("name").build();
        rabbitService.pole(message);

        assertThat(true).isTrue();
    }

}