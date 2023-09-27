package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import com.koopey.api.service.impl.IMessageService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

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

    public Long countByReceiver(UUID receiverId) {
        return messageRepository.countByReceiverId(receiverId);
    }

    public Long countByReceiverAndType(UUID receiverId, String type) {
        return messageRepository.countByReceiverIdAndType(receiverId, type);
    }

    public Long countByReceiverOrSender(UUID receiverId, UUID senderId) {
        return messageRepository.countByReceiverIdOrSenderId(receiverId, senderId);
    }

    public Long countByReceiverOrSenderAndType(UUID receiverId, UUID senderId, String type) {
        return messageRepository.countByReceiverIdOrSenderId_AndType(receiverId, senderId, type);
    }

    public Long countBySender(UUID senderId) {
        return messageRepository.countBySenderId(senderId);
    }

    public Long countBySenderAndType(UUID senderId, String type) {
        return messageRepository.countBySenderIdAndType(senderId, type);
    }

    public List<Message> findByReceiver(UUID userId) {
        return this.messageRepository.findByReceiverId(userId);
    }

    public Page<List<Message>> findByReceiver(UUID userId, Pageable pagable) {
        return this.messageRepository.findByReceiverId(userId, pagable);
    }

    public List<Message> findByReceiverAndType(UUID userId, String type) {
        return this.messageRepository.findByReceiverIdAndType(userId, type);
    }

    public Page<List<Message>> findByReceiverAndType(UUID userId, String type, Pageable pagable) {
        return this.messageRepository.findByReceiverIdAndType(userId, type, pagable);
    }

    public List<Message> findByReceiverOrSender(UUID userId) {
        return this.messageRepository.findByReceiverIdOrSenderId(userId, userId);
    }

    public List<Message> findByReceiverOrSenderAndType(UUID userId, String type) {
        return this.messageRepository.findByReceiverIdOrSenderId_AndType(userId, userId, type);
    }

    public Page<List<Message>> findByReceiverOrSenderAndType(UUID userId, String type, Pageable pagable) {
        return this.messageRepository.findByReceiverIdOrSenderId_AndType(userId, userId, type, pagable);
    }

    public Page<List<Message>> findByReceiverOrSender(UUID userId, Pageable pagable) {
        return this.messageRepository.findByReceiverIdOrSenderId(userId, userId, pagable);
    }

    public List<Message> findBySender(UUID userId) {
        return this.messageRepository.findBySenderId(userId);
    }

    public Page<List<Message>> findBySender(UUID userId, Pageable pagable) {
        return this.messageRepository.findBySenderId(userId, pagable);
    }

    public List<Message> findBySenderAndType(UUID userId, String type) {
        return this.messageRepository.findBySenderIdAndType(userId, type);
    }

    public Page<List<Message>> findBySenderAndType(UUID userId, String type, Pageable pagable) {
        return this.messageRepository.findBySenderIdAndType(userId, type, pagable);
    }

}
