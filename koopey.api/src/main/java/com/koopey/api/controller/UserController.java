package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.entity.Search;
import com.koopey.api.model.entity.User;
import com.koopey.api.service.UserService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private UserService userService;

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody User user) {

        userService.delete(user);       

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping("/read/{userId}")
    public ResponseEntity<User> read(@PathVariable("userId") UUID userId) {

        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "read/me", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> readMyUser(@RequestHeader(name = "Authorization") String authenticationHeader) {    
       
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
       
        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Fatal error. Token corrupt.", HttpStatus.BAD_REQUEST);
        } else {

            Optional<User> user = userService.findById(id);
        
            if (user.isPresent()){
                return new ResponseEntity<Object>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
            }            
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> search(@RequestBody Search search) {

        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
    }
}