package com.koopey.api.controller;

import com.koopey.api.configuration.JwtTokenUtil;
import com.koopey.api.model.dto.UserAutheticateDto;
import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.parser.UserParser;
import com.koopey.api.model.authentication.AuthToken;
import com.koopey.api.repository.UserRepository;
import com.koopey.api.service.AuthenticationService;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;

import java.text.ParseException;
import java.util.logging.Level;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @CrossOrigin(origins = "http://localhost:1709", maxAge = 3600,
// allowCredentials = "false")
@RestController
@RequestMapping("authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthToken> login(@RequestBody UserAutheticateDto loginUser) throws AuthenticationException {
        log.info("Post to authentication login");
        return ResponseEntity.ok(authenticationService.login(loginUser));
    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody UserRegisterDto userDto) throws ParseException {

        log.info("Post to authentication register");
        User user = new UserParser().convertToEntity(userDto);

        if (user.getAvatar() == null || user.getEmail().isEmpty() || user.getEmail() == null || user.getEmail().isEmpty() || user.getName() == null || user.getName().isEmpty() || user.getMobile() == null
                || user.getMobile().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Please supply all required fields.");
        } else if (authenticationService.checkIfUserExists(user)) {
            return ResponseEntity.unprocessableEntity().body("User already registered. Please recover your account.");
        } else if (authenticationService.checkIfAliasExists(user)) {
            return ResponseEntity.unprocessableEntity()
                    .body("Alias or Username already exists. Please choose a different alias.");
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParserException(ParseException e) {
      log.error( e.getMessage());
      return new ResponseEntity<>("Please supply all required fields in resgiter. " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}