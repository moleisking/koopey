package com.koopey.api.controller;

import com.koopey.api.configuration.JwtTokenUtil;
import com.koopey.api.model.LoginUser;
import com.koopey.api.model.User;
import com.koopey.api.model.Authentication.AuthToken;
import com.koopey.api.repository.UserRepository;

import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
//@CrossOrigin(origins = "http://localhost:1709", maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("authenticate")
public class AuthenticationController {

    private static Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthToken> login(@RequestBody LoginUser loginUser) throws AuthenticationException {
        log.info("login call 1: {} {}", loginUser.getUsername(), loginUser.getPassword());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        log.info("login call 2");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("login call 3");
        final User user = userRepository.findByUsername(loginUser.getUsername());
        log.info("login call 4");
        final String token = jwtTokenUtil.generateToken(user);
        log.info("login call 5: {}", token);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody User user) {
        log.info("register call");
        if (user.getId().equals("") && userRepository.existsById(user.getId())) {
            return ResponseEntity.unprocessableEntity().body("User already registered. Please recover your account.");
        } else if (user.getEmail().isEmpty() || user.getMobile().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.unprocessableEntity().body("Please supply all required fields.");
        } else if (user.getEmail().isEmpty() || user.getMobile().isEmpty()
                || userRepository.existsByEmailOrMobile(user.getEmail(), user.getMobile())) {
            return ResponseEntity.unprocessableEntity().body("User already registered. Please recover your account.");
        } else if (user.getUsername().isEmpty() || userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.unprocessableEntity()
                    .body("Alias or Username already exists. Please choose a different alias.");
        } else {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(user);
        }
    }
}