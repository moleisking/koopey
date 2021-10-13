package com.koopey.api.repository;

import com.koopey.api.model.entity.Conversation;
import com.koopey.api.model.entity.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends BaseRepository<Conversation, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM Conversation C " + "WHERE user_id = :user_id "
            + "AND message_id = :message_id")
    public int findDuplicate(@Param("user_id") UUID userId, @Param("message_id") UUID messageId);

    @Query(nativeQuery = true, value = "SELECT Message.* FROM Conversation C"
            + "INNER JOIN Message M ON C.user_id = M.user_id " + "WHERE user_id = :user_id")
    public List<Message> findUserMessages(@Param("user_id") UUID userId);

    public List<Conversation> findByMessageId(@Param("message_id") UUID messageId);

    public List<Conversation> findByUserId(@Param("user_id") UUID userId);
}
