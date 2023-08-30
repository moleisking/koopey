package com.koopey.api.service;

import com.koopey.api.model.entity.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RabbiteServiceTests {

    @Autowired
    private RabbitService rabbitService;

    @Test
    public void testSendOneMessage() {
        Message m = new Message();
        m.setSenderId(UUID.randomUUID());
        m.setReceiverId(UUID.randomUUID());
        m.setDescription("Hello World again 3!");
        m.setName("Name");
        rabbitService.send(m);
    }

    @Test
    public void testReadOneMessage() {
        Message m = new Message();
        m.setSenderId(UUID.randomUUID());
        m.setReceiverId(UUID.randomUUID());
        m.setDescription("Hello World again 3!");
        m.setName("Name");
        rabbitService.send(m);
    }

}