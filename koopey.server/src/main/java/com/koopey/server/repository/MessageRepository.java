package com.koopey.server.repository;

import com.koopey.server.model.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID>{

    @Query(nativeQuery = true, value = "SELECT * FROM Message m WHERE sender_id = :userId OR receiver_id = :userId ")
    public List<Message> findBySenderOrReceiver(@Param("userId") String userId);

    @Query(nativeQuery = true, value = "SELECT count(id) FROM Message m WHERE sender_id = :userId OR receiver_id = :userId ")
    public int countBySenderOrReceiver(@Param("userId") String userId);

}