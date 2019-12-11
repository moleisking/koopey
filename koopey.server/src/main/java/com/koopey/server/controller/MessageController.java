package com.koopey.server.controller;

import java.util.List;
import java.util.Optional;

import com.koopey.server.data.MessageRepository;
import com.koopey.server.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("messages")
public class MessageController  {
    
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("create")
    public ResponseEntity<Void> putUser(@RequestBody Message message) { 

        messageRepository.save(message);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable("messageId") String messageId) {

        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()){
            return new ResponseEntity<Message> (message.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Message> (message.get(), HttpStatus.NOT_FOUND);
        }      
     
    }

    @GetMapping("")
    public ResponseEntity<List<Message>> getMessages() {

        return  new ResponseEntity<List<Message>> (messageRepository.findAll(), HttpStatus.OK);        
    }
}