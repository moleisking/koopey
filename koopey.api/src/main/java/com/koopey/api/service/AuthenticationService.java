package com.koopey.api.service;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.configuration.jwt.JwtTokenUtil;
import com.koopey.api.model.authentication.AuthToken;
import com.koopey.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    public AuthToken login(AuthenticationDto loginUser) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUser.getAlias(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userRepository.findByUsernameOrEmail(loginUser.getAlias(), loginUser.getEmail());
        final String token = jwtTokenUtil.generateToken(user);
        return new AuthToken(user.getId(), token);
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

    public void changePassword(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        if (userRepository.save(user) == null) {
            log.error("Password change fail for {}", user.getAlias());            
        } else {
            log.info("Password change for {}", user.getAlias());            
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
