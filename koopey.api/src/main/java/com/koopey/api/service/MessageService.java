package com.koopey.api.service;

import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends AuditService<Message, UUID> {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageRepository messageRepository;
  
    MessageService(@Lazy KafkaTemplate<String, String> kafkaTemplate, @Lazy MessageRepository messageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRepository = messageRepository;
    }

    protected AuditRepository<Message, UUID> getRepository() {
        return messageRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate(){
        return kafkaTemplate;
    }

    public long count() {
        return messageRepository.count();
    }

    public Long countByDeliveredAndReceiver(Boolean delivered, UUID receiverId) {
        return messageRepository.countByDeliveredAndReceiverId(delivered, receiverId);
    }

    public Long countByDeliveredAndSender(Boolean delivered, UUID senderId) {
        return messageRepository.countByDeliveredAndSenderId(delivered, senderId);
    }

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
