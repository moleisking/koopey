package com.koopey.api.repository;

import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.base.AuditRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends AuditRepository<Message, UUID> {

        public long countByDeliveredAndReceiverId( Boolean delivered, UUID receiverId);

        public long countByDeliveredAndSenderId(Boolean delivered, UUID senderId);

        public long countByReceiverIdOrSenderId(UUID receiverId, UUID senderId);

        public long countByReceiverId(UUID receiverId);

        public long countBySenderId(UUID senderId);

        public List<Message> findByDeliveredAndSenderId( Boolean delivered , UUID senderId);

        public List<Message> findByDeliveredAndReceiverId( Boolean delivered , UUID receiverId);

        public List<Message> findByReceiverIdOrSenderId(UUID receiverId, UUID senderId);

        public Page<List<Message>> findByReceiverIdOrSenderId(UUID receiverId, UUID senderId, Pageable pagable);

}