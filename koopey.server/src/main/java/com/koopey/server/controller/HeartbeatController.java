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
}