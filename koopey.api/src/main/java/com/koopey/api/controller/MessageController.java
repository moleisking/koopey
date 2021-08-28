package com.koopey.api.controller;

import com.koopey.api.model.entity.Message;
import com.koopey.api.service.MessageService;
import java.util.List;
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
@RequestMapping("message")
public class MessageController  {
     
    @Autowired
    private MessageService messageService;

    @PostMapping(value="create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Message message) {      
        messageService.save(message);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Message message) {      
        messageService.delete(message);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Message message) {        
        messageService.save(message);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value= "read/my/messages/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> readMyMessages(@PathVariable("userId") String userId) {

        List<Message> messages = messageService.findBySenderOrReceiver(userId);

        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);     
    }

    @GetMapping(value= "read/{messageId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Message> read(@PathVariable("messageId") UUID messageId) {

        Optional<Message> message = messageService.findById(messageId);

        if (message.isPresent()){
            return new ResponseEntity<Message> (message.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Message> (message.get(), HttpStatus.NOT_FOUND);
        }      
     
    }

    @PostMapping(value= "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> search(@RequestBody Message message) {

        List<Message> messages=  messageService.findAll();     

        if (messages.size() > 0) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        }
    }
}