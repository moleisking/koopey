package com.koopey.api.controller;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.parser.UserParser;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.AuthenticationException;
import com.koopey.api.model.authentication.AuthenticationToken;
import com.koopey.api.service.AuthenticationService;
import java.text.ParseException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomProperties customProperties;  

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> login(@RequestBody AuthenticationDto loginUser) throws AuthenticationException {
        log.info("Post to authentication login");
       
        AuthenticationToken authToken = authenticationService.login(loginUser);
        if (!authToken.getToken().isEmpty()){
            log.info("Post to authentication login success");
            return new ResponseEntity<Object>(authToken , HttpStatus.OK);
        } else {
             log.info("Post to authentication login fail");
            return new  ResponseEntity<Object>("Fatal error. Token not generated.",  HttpStatus.BAD_REQUEST);
        }
       
    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")   
    public ResponseEntity<Object> register(@RequestBody UserRegisterDto userDto) throws ParseException {

        log.info("Post to register new user");
        User user = UserParser.convertToEntity(userDto);      
        if (user.getAvatar() == null || user.getEmail().isEmpty() || user.getEmail() == null
                || user.getEmail().isEmpty() || user.getName() == null || user.getName().isEmpty()
                || user.getMobile() == null || user.getMobile().isEmpty() || user.getPassword() == null
                || user.getPassword().isEmpty() || user.getAlias() == null || user.getAlias().isEmpty()) {
                    log.info("Bad user register dto");
            return new ResponseEntity<Object>("Please supply all required fields.",  HttpStatus.BAD_REQUEST);
        } else if (authenticationService.checkIfUserExists(user)) {
            log.info("Duplicate user register action detected");
            return new ResponseEntity<Object>("User already registered. Please recover your account.",  HttpStatus.NOT_ACCEPTABLE);
        } else if (authenticationService.checkIfAliasExists(user)) {
            log.info("Duplicate alias register action detected");
            return new ResponseEntity<Object>("Alias or Username already exists. Please choose a different alias.", HttpStatus.NOT_ACCEPTABLE);
        } else if (user.getAvatar().length() >=  customProperties.getAvatarMaxSize()) {
            log.info("Avatar is to large. Please choose a smaller avatar.");
            return new ResponseEntity<Object>("Avatar is to large. Please choose a smaller avatar.", HttpStatus.NOT_ACCEPTABLE);
        } else if (authenticationService.register(user)) {
            log.info("New user register action successful for {}", user.getAlias());
            return new ResponseEntity<Object>(user, HttpStatus.CREATED);
        } else {
            log.info("Fatal error of unknown cause. Bad request.");
            return new ResponseEntity<Object>("Fatal error of unknown cause.",  HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

     @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleParserException(AuthenticationException e) {
      //  log.error(e.getMessage());
        return new ResponseEntity<>("Authentication fail. " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParserException(ParseException e) {
       // log.error(e.getMessage());
        return new ResponseEntity<>("Please supply all required fields in resgiter. " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}