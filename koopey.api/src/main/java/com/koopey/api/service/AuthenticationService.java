package com.koopey.api.service;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.AuthenticationException;
import com.koopey.api.model.authentication.AuthenticationUser;
import com.koopey.api.repository.LocationRepository;
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

    public AuthenticationUser login(AuthenticationDto loginUser) throws AuthenticationException {

        if ((loginUser.getAlias() == null) && (loginUser.getEmail() == null)) {
            throw new AuthenticationException("Empty alias and email");
        } else if ((loginUser.getAlias() == null || loginUser.getAlias().isEmpty())
                && !(loginUser.getEmail() == null && loginUser.getEmail().isEmpty())) {
            loginUser.setAlias(userRepository.findAliasByEmail(loginUser.getEmail()));
            return this.getAuthenticationUser(loginUser);
        } else {
            return this.getAuthenticationUser(loginUser);
        }

    }

    public Boolean register(User user) {

        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getMobile() == null
                || user.getMobile().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getAlias() == null || user.getAlias().isEmpty()) {
            return false;
        } else if (userRepository.existsByEmailOrIdOrMobile(user.getEmail(), user.getId(), user.getMobile())) {
            return false;
        } else if (user.getAlias().isEmpty() || userRepository.existsByAlias(user.getAlias())) {
            return false;
        } else {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);            
            log.info("User register {}", user.getAlias());
            if (customProperties.getVerificationEnable()) {
                smtpService.sendSimpleMessage(user.getEmail(), customProperties.getEmailAddress(), "subject",
                        customProperties.getVerificationUrl());
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

        if (userRepository.existsByEmailOrIdOrMobile(user.getEmail(), user.getId(), user.getMobile())) {
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

    private AuthenticationUser getAuthenticationUser(AuthenticationDto loginUser) {

        final Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginUser.getAlias(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userRepository.findByAliasOrEmail(loginUser.getAlias(), loginUser.getEmail());
        final String token = jwtTokenUtility.generateToken(user);
        return new AuthenticationUser(token, user);
    }

}
