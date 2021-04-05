package com.koopey.server.controller;

import com.koopey.server.model.Message;
import com.koopey.server.repository.MessageRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("messages")
public class MessageController  {
    
    private static Logger LOGGER = Logger.getLogger(MessageController.class.getName());

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping(value="create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Message message) { 
        LOGGER.log(Level.INFO, "create(" + message.getId() + ")");
        messageRepository.save(message);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Message message) {
        LOGGER.log(Level.INFO, "delete(" + message.getId() + ")");
        messageRepository.delete(message);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Message message) {
        LOGGER.log(Level.INFO, "delete(" + message.getId() + ")");      
        messageRepository.save(message);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value= "read/my/messages/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> readMyMessages(@PathVariable("userId") String userId) {

        List<Message> messages = messageRepository.findBySenderOrReceiver(userId);

        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);     
    }

    @GetMapping(value= "read/{messageId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Message> read(@PathVariable("messageId") UUID messageId) {

        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()){
            return new ResponseEntity<Message> (message.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Message> (message.get(), HttpStatus.NOT_FOUND);
        }      
     
    }

    @PostMapping(value= "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> search(@RequestBody Message message) {

        List<Message> messages=  messageRepository.findAll();     

        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }
}