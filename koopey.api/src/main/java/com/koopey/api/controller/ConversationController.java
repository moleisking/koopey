package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.entity.Conversation;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.type.UserType;
import com.koopey.api.service.ConversationService;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("conversation")
public class ConversationController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private ConversationService conversationService;

    @GetMapping(value = "count", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> count() {
        Long count = conversationService.count();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @GetMapping(value = "count/not/sent", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countNotSent(@RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = conversationService.countByTypeAndSentAndUserId(UserType.SENDER, false, id);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @GetMapping(value = "count/not/received", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countNotArrive(@RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long count = conversationService.countByTypeAndReceivedAndUserId(UserType.RECEIVER, false, id);
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody Conversation conversation) {
        conversation = conversationService.save(conversation);
        return new ResponseEntity<UUID>(conversation.getId(),HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Conversation conversation) {
        conversationService.delete(conversation);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Conversation conversation) {
        conversationService.save(conversation);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{conversationId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Conversation> read(@PathVariable("conversationId") UUID conversationId) {

        Optional<Conversation> conversation = conversationService.findById(conversationId);

        if (conversation.isPresent()) {
            return new ResponseEntity<Conversation>(conversation.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Conversation>(conversation.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "search/by/messages/{messageId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Conversation>> searchByMessages(@PathVariable("messageId") UUID messageId) {

        List<Conversation> conversations = conversationService.findByMessageId(messageId);

        if (conversations.isEmpty()) {
            return new ResponseEntity<List<Conversation>>(conversations, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Conversation>>(conversations, HttpStatus.OK);
        }

    }

    @GetMapping(value = "search/by/my/messages", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> searchByMyMessages(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Message>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
        }

        List<Message> messages = conversationService.findMessages(id);

        if (messages.isEmpty()) {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/users/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Conversation>> searchByUsers(@PathVariable("userId") UUID userId) {

        List<Conversation> conversations = conversationService.findByUserId(userId);

        if (conversations.isEmpty()) {
            return new ResponseEntity<List<Conversation>>(conversations, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Conversation>>(conversations, HttpStatus.OK);
        }

    }

    @GetMapping(value = "search/by/my/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> searchByMyUser(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<User>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
        }

        List<User> users = conversationService.findUsers(id);

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }

    }

}
