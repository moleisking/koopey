package com.koopey.api.service;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.authentication.AuthenticationToken;
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
    CustomProperties customProperties;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private SmtpService smtpService;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationToken login(AuthenticationDto loginUser) {

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUser.getAlias(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userRepository.findByAliasOrEmail(loginUser.getAlias(), loginUser.getEmail());
        final String token = jwtTokenUtility.generateToken(user);
        return new AuthenticationToken( token);
    }

    public Boolean register(User user) {

        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getMobile() == null
                || user.getMobile().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getAlias() == null || user.getAlias().isEmpty()) {
            return false;
        } else if (userRepository.existsByEmailOrMobile(user.getEmail(), user.getMobile())) {
            return false;
        } else if (user.getAlias().isEmpty() || userRepository.existsByAlias(user.getAlias())) {
            return false;
        } else {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
            if(customProperties.getVerificationEnable()){
                smtpService.sendSimpleMessage(user.getEmail(), customProperties.getEmailAddress(), "subject", customProperties.getVerificationUrl());
            }            
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
        
        if (user.getAlias().isEmpty() || userRepository.existsByAlias(user.getAlias())) {
            return true;
        } else {
            return false;
        }
    }

}
