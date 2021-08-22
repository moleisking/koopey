package com.koopey.api.controller;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.parser.UserParser;
import com.koopey.api.model.authentication.AuthToken;
import com.koopey.api.service.AuthenticationService;
import java.text.ParseException;
import javax.naming.AuthenticationException;
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

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> login(@RequestBody AuthenticationDto loginUser) throws AuthenticationException {
        log.info("Post to authentication login");
        log.info(loginUser.toString());
        AuthToken authToken = authenticationService.login(loginUser);
        if (!authToken.getToken().isEmpty()){
            return ResponseEntity.ok(authenticationService.login(loginUser));
        } else {
            return ResponseEntity.badRequest().body("Fatal error. Token not generated.");
        }
       
    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody UserRegisterDto userDto) throws ParseException {

        log.info("Post to register new user");
        User user = new UserParser().convertToEntity(userDto);

        if (user.getAvatar() == null || user.getEmail().isEmpty() || user.getEmail() == null
                || user.getEmail().isEmpty() || user.getName() == null || user.getName().isEmpty()
                || user.getMobile() == null || user.getMobile().isEmpty() || user.getPassword() == null
                || user.getPassword().isEmpty() || user.getUsername() == null || user.getUsername().isEmpty()) {
                    log.info("Bad user register dto");
            return ResponseEntity.badRequest().body("Please supply all required fields.");
        } else if (authenticationService.checkIfUserExists(user)) {
            log.info("Duplicate user register action detected");
            return ResponseEntity.unprocessableEntity().body("User already registered. Please recover your account.");
        } else if (authenticationService.checkIfAliasExists(user)) {
            log.info("Duplicate alias register action detected");
            return ResponseEntity.unprocessableEntity()
                    .body("Alias or Username already exists. Please choose a different alias.");
        } else if (authenticationService.register(user)) {
            log.info("New user register action successful for {}", user.getUsername());
            return ResponseEntity.ok(user);
        } else {
            log.info("Fatal error of unknown cause. Bad request.");
            return ResponseEntity.badRequest().body("Fatal error of unknown cause.");
        }
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParserException(ParseException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Please supply all required fields in resgiter. " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}