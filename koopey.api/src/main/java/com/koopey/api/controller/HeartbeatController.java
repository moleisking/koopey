package com.koopey.api.controller;

import com.koopey.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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