package com.koopey.api.service.impl;

import com.koopey.api.model.entity.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMessageService {

    long count();

    Long countByReceiver(UUID userId);

    Long countByReceiverAndType(UUID userId, String type);

    Long countByReceiverOrSender(UUID receiverId, UUID senderId);

    Long countByReceiverOrSenderAndType(UUID receiverId, UUID senderId, String type);

    Long countBySender(UUID senderId);

    Long countBySenderAndType(UUID senderId, String type);

    List<Message> findByReceiver(UUID userId);

    List<Message> findByReceiverAndType(UUID userId, String type);

    List<Message> findBySender(UUID userId);

    List<Message> findBySenderAndType(UUID userId, String type);

    List<Message> findByReceiverOrSender(UUID userId);

    List<Message> findByReceiverOrSenderAndType(UUID userId, String type);

    Page<List<Message>> findByReceiver(UUID userId, Pageable pagable);

    Page<List<Message>> findByReceiverAndType(UUID userId, String type, Pageable pagable);

    Page<List<Message>> findByReceiverOrSender(UUID userId, Pageable pagable);

    Page<List<Message>> findByReceiverOrSenderAndType(UUID userId, String type, Pageable pagable);

    Page<List<Message>> findBySender(UUID userId, Pageable pagable);

    Page<List<Message>> findBySenderAndType(UUID userId, String type, Pageable pagable);
}
