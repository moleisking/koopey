package com.koopey.api.repository;

import com.koopey.api.model.entity.Conversation;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.type.UserType;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends BaseRepository<Conversation, UUID> {

        public long countByMessageIdAndUserId( UUID messageId,  UUID userId);   
       
        public long countByType( UserType type);

        public long countByTypeAndReceived(UserType type, Boolean sent);

        public long countByTypeAndReceivedAndSent(UserType type, Boolean sent, Boolean received);

        public long countByTypeAndSent(UserType type, Boolean send);    

        public long countByTypeAndReceivedAndUserId(UserType type, Boolean sent, UUID userId);

        public long countByTypeAndReceivedAndSentAndUserId(UserType type, Boolean sent, Boolean received, UUID userId);

        public long countByTypeAndSentAndUserId(UserType type, Boolean sent, UUID userId);  

        public List<Conversation> findByMessageId(@Param("message_id") UUID messageId);

        public List<Conversation> findByUserId(@Param("user_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT Message.* FROM Conversation C"
                        + "INNER JOIN Message M ON M.id = C.message_id " + "WHERE M.id = :message_id")
        public List<Message> findMessages(@Param("message_id") UUID messageId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Conversation C"
                        + "INNER JOIN User U ON U.id = C.user_id " + "WHERE U.id = :user_id")
        public List<User> findUsers(@Param("user_id") UUID userId);

}
