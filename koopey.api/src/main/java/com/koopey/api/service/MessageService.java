package com.koopey.api.service;

import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.MessageRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends BaseService <Message, UUID> {
    
    @Autowired
    MessageRepository messageRepository;

    BaseRepository<Message, UUID> getRepository() {       
        return messageRepository;
    }

    public List<Message> findBySenderOrReceiver(String userId){
        return messageRepository.findBySenderOrReceiver(userId);

    }
   
    public int countBySenderOrReceiver( String userId){
        return messageRepository.countBySenderOrReceiver(userId);
    }
}
