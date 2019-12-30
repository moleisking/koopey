package com.koopey.server.controller;

import com.koopey.server.data.UserRepository;
import com.koopey.server.model.User;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private static Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody User user) {
        LOGGER.log(Level.INFO, "create(" + user.getId() + ")");
        userRepository.save(user);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody User user) {
        LOGGER.log(Level.INFO, "delete(" + user.getId() + ")");
        userRepository.delete(user);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody User user) {
        LOGGER.log(Level.INFO, "delete(" + user.getId() + ")");      
        userRepository.save(user);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping("read/{userId}")
    public ResponseEntity<User> read(@PathVariable("userId") String assetId) {

        Optional<User> user = userRepository.findById(assetId);

        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user.get(), HttpStatus.NOT_FOUND);
        }
    }
}