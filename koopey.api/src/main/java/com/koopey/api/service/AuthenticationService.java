package com.koopey.api.service;

import org.springframework.stereotype.Service;

import com.koopey.api.configuration.JwtTokenUtil;
import com.koopey.api.model.dto.UserAutheticateDto;
import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.authentication.AuthToken;
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
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;
   

    public AuthToken login(UserAutheticateDto loginUser) {
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
        return new AuthToken(token);
    }

    public Boolean register(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getMobile() == null
                || user.getMobile().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getUsername() == null || user.getUsername().isEmpty()) {
            return false;
        } else if (userRepository.existsByEmailOrMobile(user.getEmail(), user.getMobile())) {
            return false;
        } else if (user.getUsername().isEmpty() || userRepository.existsByUsername(user.getUsername())) {
            return false;
        } else {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
            return true;
        }
    }

    public Boolean checkIfUserExists(User user) {
        if (userRepository.existsByEmailOrMobile(user.getEmail(), user.getMobile())) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkIfAliasExists(User user) {
        if (user.getUsername().isEmpty() || userRepository.existsByUsername(user.getUsername())) {
            return true;
        } else {
            return false;
        }
    }

}
