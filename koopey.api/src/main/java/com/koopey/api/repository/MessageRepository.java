package com.koopey.api.repository;

import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID> {

        public long countByReceiverIdOrSenderId(UUID receiverId, UUID senderId);

        public long countByReceiverIdOrSenderId_AndType(UUID receiverId, UUID senderId, String type);

        public long countByReceiverId(UUID receiverId);

        public long countByReceiverIdAndType(UUID receiverId, String type);

        public long countBySenderId(UUID senderId);

        public long countBySenderIdAndType(UUID senderId, String type);

        public List<Message> findByReceiverId(UUID receiverId);

        public List<Message> findByReceiverIdAndType(UUID receiverId, String type);

        public List<Message> findByReceiverIdOrSenderId(UUID receiverId, UUID senderId);

        public List<Message> findByReceiverIdOrSenderId_AndType(UUID receiverId, UUID senderId, String type);

        public List<Message> findBySenderId(UUID senderId);

        public List<Message> findBySenderIdAndType(UUID senderId, String type);

        public Page<List<Message>> findByReceiverId(UUID receiverId, Pageable pageable);

        public Page<List<Message>> findByReceiverIdAndType(UUID receiverId, String type, Pageable pageable);

        public Page<List<Message>> findByReceiverIdOrSenderId(UUID receiverId, UUID senderId, Pageable pageable);

        public Page<List<Message>> findByReceiverIdOrSenderId_AndType(UUID receiverId, UUID senderId, String type,
                        Pageable pageable);

        public Page<List<Message>> findBySenderId(UUID senderId, Pageable pageable);

        public Page<List<Message>> findBySenderIdAndType(UUID senderId, String type, Pageable pageable);

}