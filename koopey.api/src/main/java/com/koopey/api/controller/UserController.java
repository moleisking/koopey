package com.koopey.api.controller;

import com.koopey.api.model.entity.Search;
import com.koopey.api.model.entity.User;
import com.koopey.api.repository.UserRepository;
import com.koopey.api.service.UserService;
import com.koopey.api.view.UserResponse;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

      @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

   /* @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        LOGGER.log(Level.INFO, "create(" + user.getId() + ")");

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
        // return new ResponseEntity<Void>(HttpStatus.OK);
    }*/

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody User user) {
       
        userService.delete(user);
        // check if image and reviews deleted

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

    @PostMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> search(@RequestBody Search search) {    

        List<User> users= userService.findAll();     

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);           
        }
    }
}