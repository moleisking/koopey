package com.koopey.api.repository.transaction;

import com.koopey.api.model.dto.ConversationDto;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.entity.User;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ConversationQuery {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteUser(User user) {
        entityManager.createNativeQuery("DELETE FROM Conversation WHERE user_id = ?")
          .setParameter(1, user.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteMessage(Message message) {
        entityManager.createNativeQuery("DELETE FROM Conversation WHERE message_id = ?")
          .setParameter(1, message.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insert(ConversationDto conversation) {
        entityManager.createNativeQuery("INSERT INTO Conversation (id, user_id, message_id) VALUES (?,?,?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, conversation.getUserId().replace("-", ""))
          .setParameter(3, conversation.getMessageId().replace("-", ""))
          .setParameter(4, conversation.getReceived())
          .setParameter(5, conversation.getSent())
          .executeUpdate();
    }
}
