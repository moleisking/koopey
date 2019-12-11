package com.koopey.server.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.koopey.server.data.UserRepository;
import com.koopey.server.model.User;
import com.koopey.server.view.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> putUser(@RequestBody User user) {
        System.out.println("putUser");
        userRepository.save(user);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/read/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {

        Optional<User> user = userRepository.findById(userId);

        //UserResponse userResponse = UserResponse.builder()
        //.id(user.getId() ).build();        


        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user.get(), HttpStatus.NOT_FOUND);
        }
    } 

    @PostMapping(path = "/delete/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        System.out.println("putUser");
        userRepository.delete(user);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
//Collections.emptyList()
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("ping")
    public String getPing() { 

        return "Hello world!";
    }
}