package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaService {

    @Autowired
    private CustomProperties customProperties;
    private final MessageRepository messageRepository;
    private final KafkaTemplate kafkaTemplate;

    KafkaService(@Lazy MessageRepository messageRepository, @Lazy KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRepository = messageRepository;
    }

}
