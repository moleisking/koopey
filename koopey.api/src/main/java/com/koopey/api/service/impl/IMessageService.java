package com.koopey.api.service.impl;

import com.koopey.api.model.entity.Message;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMessageService {

    long count();

   // Long countByDeliveredAndReceiver(Boolean delivered, UUID receiverId);

   // Long countByDeliveredAndSender(Boolean delivered, UUID senderId);

    Long countByReceiverOrSender(UUID receiverId, UUID senderId);

    Long countByReceiver(UUID receiverId);

    Long countBySender(UUID senderId);

    List<Message> findByDeliveredAndReceiver(Boolean delivered, UUID userId);

    List<Message> findByDeliveredAndSender(Boolean delivered, UUID userId);

    List<Message> findByReceiverOrSender(UUID userId);

    Page<List<Message>> findByReceiverOrSender(UUID userId, Pageable pagable);
}
