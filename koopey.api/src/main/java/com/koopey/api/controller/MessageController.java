package com.koopey.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.parser.AssetParser;
import com.koopey.api.model.parser.MessageParser;
import com.koopey.api.service.MessageService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private MessageService messageService;

    private MessageParser messageParser = new MessageParser();

    @GetMapping(value = "count", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> count() {
        Long count = messageService.count();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }  

    @GetMapping(value = "count/by/receiver/or/sender", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countByReceiverOrSender(
            @RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = messageService.countByReceiverOrSender(id, id);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @GetMapping(value = "count/by/receiver", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countByReceiver(@RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = messageService.countByReceiver(id);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @GetMapping(value = "count/by/sender", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countBySender(@RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = messageService.countBySender(id);
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

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Message message) {
        messageService.delete(message);
        return new ResponseEntity<Void>(HttpStatus.OK);
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

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/receiver/or/sender", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByReceiverOrSender(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = messageService.findByReceiverOrSender(id);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

   /*  @GetMapping(value = "search/by/delivered/and/sender", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByDeliveredAndSender(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = messageService.findByDeliveredAndSender(true, id);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/delivered/and/receiver", consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByDeliveredAndReceiver(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = messageService.findByDeliveredAndReceiver(true, id);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/not/delivered/and/sender", consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByNotDeliveredAndSender(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = messageService.findByDeliveredAndSender(false, id);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/not/delivered/and/receiver", consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByNotDeliveredAndReceiver(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Message> messages = messageService.findByDeliveredAndReceiver(false, id);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }*/

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, IOException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, JsonProcessingException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, ParseException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
}