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
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "count", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> count() {
        Long count = messageService.count();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody Message message) {
        message = messageService.save(message);
        return new ResponseEntity<UUID>( message.getId(), HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Message message) {
        messageService.delete(message);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Message message) {
        messageService.save(message);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{messageId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Message> read(@PathVariable("messageId") UUID messageId) {

        Optional<Message> message = messageService.findById(messageId);

        if (message.isPresent()) {
            return new ResponseEntity<Message>(message.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Message>(message.get(), HttpStatus.NOT_FOUND);
        }
    }    

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> search(@RequestBody Message message) {

        List<Message> messages = messageService.findAll();

        if (messages.isEmpty()){
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);            
        }
    }
}