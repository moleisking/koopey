package com.koopey.api.repository;

import com.koopey.api.model.entity.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID>{

    @Query(nativeQuery = true, value = "SELECT * FROM Message m WHERE sender_id = :userId OR receiver_id = :userId ")
    public List<Message> findBySenderOrReceiver(@Param("userId") UUID userId);

    @Query(nativeQuery = true, value = "SELECT count(id) FROM Message m WHERE sender_id = :userId OR receiver_id = :userId ")
    public int countBySenderOrReceiver(@Param("userId") UUID userId);

    @Query(nativeQuery = true, value = "SELECT count(id) FROM Message m WHERE (sender_id = :userId OR receiver_id = :userId) AND sent = :sent")
    public int countBySenderOrReceiverAndSent(@Param("userId") UUID userId, Boolean sent);

    @Query(nativeQuery = true, value = "SELECT count(id) FROM Message m WHERE (sender_id = :userId OR receiver_id = :userId) AND arrive = :arrive")
    public int countBySenderOrReceiverAndArrive(@Param("userId") UUID userId, Boolean arrive);

    public long count();

}