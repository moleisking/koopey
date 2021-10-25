package com.koopey.api.service;

import com.koopey.api.model.entity.Conversation;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.type.UserType;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.ConversationRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService extends BaseService<Conversation, UUID> {

    @Autowired
    ConversationRepository conversationRepository;

    BaseRepository<Conversation, UUID> getRepository() {
        return conversationRepository;
    }

    public long countByTypeAndReceived(UserType type, Boolean received) {
        return conversationRepository.countByTypeAndReceived(type, received);
    }

    public long countByTypeAndReceivedAndSent(UserType type, Boolean sent, Boolean received) {
        return conversationRepository.countByTypeAndReceivedAndSent(type, sent, received);
    }

    public long countByTypeAndSent(UserType type, Boolean sent) {
        return conversationRepository.countByTypeAndSent(type, sent);
    }

    public long countByTypeAndReceivedAndUserId(UserType type, Boolean received, UUID userId) {
        return conversationRepository.countByTypeAndReceivedAndUserId(type, received, userId);
    }

    public long countByTypeAndReceivedAndSentAndUserId(UserType type, Boolean sent, Boolean received, UUID userId) {
        return conversationRepository.countByTypeAndReceivedAndSentAndUserId(type, sent, received, userId);
    }

    public long countByTypeAndSentAndUserId(UserType type, Boolean sent, UUID userId) {
        return conversationRepository.countByTypeAndSentAndUserId(type, sent, userId);
    }

    public List<Conversation> findByMessageId(UUID messageId) {
        return conversationRepository.findByMessageId(messageId);
    }

    public List<Conversation> findByUserId(UUID userId) {
        return conversationRepository.findByUserId(userId);
    }

    public List<Message> findMessages(UUID messageId) {
        return conversationRepository.findMessages(messageId);
    }

    public List<User> findUsers(UUID userId) {
        return conversationRepository.findUsers(userId);
    }

}
