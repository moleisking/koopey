package com.koopey.server.controller;

import com.koopey.server.data.UserRepository;
import com.koopey.server.model.User;
import com.koopey.server.view.UserResponse;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        LOGGER.log(Level.INFO, "createUser(" + user.getId() + ")");
       return  userRepository.save(user);

       // return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        LOGGER.log(Level.INFO, "deleteUser(" + user.getId() + ")");
        userRepository.delete(user);
        return new ResponseEntity<String>("", HttpStatus.OK);
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