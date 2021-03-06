package com.koopey.api.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.koopey.api.model.User;
import com.koopey.api.repository.UserRepository;
import com.koopey.api.view.UserResponse;

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
@RequestMapping("heartbeat")
public class HeartbeatController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("beat")
    public ResponseEntity<Void> getBeat() {       
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("database")
    public ResponseEntity<String> getDatabase() {
        try {
            return ResponseEntity.ok().body(String.valueOf( userRepository.count()));
         } catch (Exception e) {
            return  ResponseEntity.ok().body( "{ db : false }" );
         }      
    }

    @GetMapping("ping")
    public ResponseEntity<String> getPing() {
        return ResponseEntity.ok().body("Hello world!");       
    }

   /* @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }*/

}