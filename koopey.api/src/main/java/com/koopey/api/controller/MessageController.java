package com.koopey.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.parser.MessageParser;
import com.koopey.api.service.JwtService;
import com.koopey.api.service.MessageService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private JwtService jwtTokenUtility;

    @Autowired
    private MessageService messageService;

    final private MessageParser messageParser = new MessageParser();

    @GetMapping(value = "count", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> count() {
        Long count = messageService.count();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }  

    @GetMapping(value = "count/by/receiver/or/sender", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countByReceiverOrSender(
            @RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam(name = "type", required = false) String type) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count =  type.isEmpty() ? messageService.countByReceiverOrSender(id, id) :
                messageService.countByReceiverOrSenderAndType(id,id,type);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @GetMapping(value = "count/by/receiver", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countByReceiver(@RequestHeader(name = "Authorization") String authenticationHeader,
                                                @RequestParam(name = "type", required = false) String type) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = type.isEmpty() ?
                messageService.countByReceiver(id) : messageService.countByReceiverAndType(id, type);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @GetMapping(value = "count/by/sender", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countBySender(@RequestHeader(name = "Authorization") String authenticationHeader,
                                              @RequestParam(name = "type", required = false) String type) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = type.isEmpty() ?
                messageService.countBySender(id) : messageService.countBySenderAndType(id, type);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody MessageDto messageDto) throws ParseException {

        Message message = messageParser.convertToEntity(messageDto);
        message = messageService.save(message);
        return new ResponseEntity<UUID>(message.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Message message) {
        messageService.delete(message);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
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

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/receiver", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByReceiver(
            @RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam(name = "type", required = false) String type) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = type.isEmpty() ?
                messageService.findByReceiverOrSender(id) : messageService.findByReceiverAndType(id, type);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/receiver/or/sender", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByReceiverOrSender(
            @RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam(name = "type", required = false) String type) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = type == null || type.isEmpty() ?
                messageService.findByReceiverOrSender(id) : messageService.findByReceiverOrSenderAndType(id, type);

        if (messages.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/sender", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchBySender(
            @RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam(name = "type", required = false) String type) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = type.isEmpty() ?
                messageService.findByReceiverOrSender(id) : messageService.findBySenderAndType(id, type);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleException(WebRequest request, IOException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleException(WebRequest request, JsonProcessingException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleException(WebRequest request, ParseException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
}