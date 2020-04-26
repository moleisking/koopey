package com.koopey.server.data;

import java.util.List;

import com.koopey.server.model.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String>{

    public List<Message> findBySenderOrReceiver(@Param("userId") String userId);

}