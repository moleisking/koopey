package com.koopey.api.repository;

import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Conversation;
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

    public List<Competition> findByMessageId(@Param("message_id") UUID messageId);

    public List<Competition> findByUserId(@Param("user_id") UUID userId);
}
