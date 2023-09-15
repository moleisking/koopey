package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import com.koopey.api.service.impl.IMessageService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService extends AuditService<Message, UUID> implements IMessageService {

    private CustomProperties customProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageRepository messageRepository;
    private final RabbitService rabbitService;

    MessageService(@Lazy KafkaTemplate<String, String> kafkaTemplate, @Lazy MessageRepository messageRepository,
            @Lazy RabbitService rabbitService) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRepository = messageRepository;
        this.rabbitService = rabbitService;
    }

    // @Override
    public void create(Message message) {
        if (customProperties.getRabbitmqEnable()) {
            rabbitService.send(message);
        } else {
            super.save(message);
        }
    }

    // @Override
    public void delete(Message message) {
        if (customProperties.getRabbitmqEnable()) {
            rabbitService.delete(message);
        } else {
            messageRepository.delete(message);
        }
    }

    public void findByMessage(Message message) {
        if (customProperties.getRabbitmqEnable()) {
            rabbitService.pole(message);
        } else {
            messageRepository.findByReceiverIdOrSenderId(message.getSenderId(), message.getReceiverId());
        }
    }

    protected AuditRepository<Message, UUID> getRepository() {
        return messageRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public long count(Message message) {
        if (customProperties.getRabbitmqEnable()) {
            return rabbitService.count(message);
        } else {
            return messageRepository.count();
        }
    }

    /*public Long countByDeliveredAndReceiver(Boolean delivered, UUID receiverId) {
        return messageRepository.countByDeliveredAndReceiverId(delivered, receiverId);
    }

    public Long countByDeliveredAndSender(Boolean delivered, UUID senderId) {
        return messageRepository.countByDeliveredAndSenderId(delivered, senderId);
    }*/

    public Long countByReceiverOrSender(UUID receiverId, UUID senderId) {
        return messageRepository.countByReceiverIdOrSenderId(receiverId, senderId);
    }

    public Long countByReceiver(UUID receiverId) {
        return messageRepository.countByReceiverId(receiverId);
    }

    public Long countBySender(UUID senderId) {
        return messageRepository.countBySenderId(senderId);
    }

    public List<Message> findByDeliveredAndReceiver(Boolean delivered, UUID userId) {
        return this.messageRepository.findByDeliveredAndReceiverId(delivered, userId);
    }

    public List<Message> findByDeliveredAndSender(Boolean delivered, UUID userId) {
        return this.messageRepository.findByDeliveredAndSenderId(delivered, userId);
    }

    public List<Message> findByReceiverOrSender(UUID userId) {
        return this.messageRepository.findByReceiverIdOrSenderId(userId, userId);
    }

    public Page<List<Message>> findByReceiverOrSender(UUID userId, Pageable pagable) {
        return this.messageRepository.findByReceiverIdOrSenderId(userId, userId, pagable);
    }

}
