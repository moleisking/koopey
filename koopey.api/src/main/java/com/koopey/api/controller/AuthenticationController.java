package com.koopey.api.controller;

import com.koopey.api.model.dto.*;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.parser.UserParser;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.AuthenticationException;
import com.koopey.api.model.authentication.AuthenticationUser;
import com.koopey.api.service.AuthenticationService;
import java.text.ParseException;
import java.util.UUID;

import com.koopey.api.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private JwtService jwtTokenUtility;

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        authenticationService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> login(@RequestBody AuthenticationDto loginUser) throws AuthenticationException {
        log.info("Post to authentication login");

        AuthenticationUser authToken = authenticationService.login(loginUser);
        if (!authToken.getToken().isEmpty()) {
            log.info("Post to authentication login success");
            return new ResponseEntity<Object>(authToken, HttpStatus.OK);
        } else {
            log.info("Post to authentication login fail");
            return new ResponseEntity<Object>("Fatal error. Token not generated.", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> register(@RequestBody UserRegisterDto userDto)
            throws HttpMessageNotReadableException, ParseException {
        log.info("Post to register new user");

        User user = UserParser.toEntity(userDto);
        if (user.getAvatar() == null || user.getEmail().isEmpty() || user.getEmail() == null
                 || user.getName() == null || user.getName().isEmpty()
                || user.getMobile() == null || user.getMobile().isEmpty() || user.getPassword() == null
                || user.getPassword().isEmpty() || user.getAlias() == null || user.getAlias().isEmpty()) {
            log.info("Bad user register dto");
            log.info(user.toString());
            return new ResponseEntity<Object>("Please supply all required fields.", HttpStatus.BAD_REQUEST);
        } else if (authenticationService.checkUserExistence(user)) {
            log.info("Duplicate user register action detected");
            return new ResponseEntity<Object>("User already registered. Please recover your account.",
                    HttpStatus.NOT_ACCEPTABLE);
        } else if (authenticationService.checkAliasExistence(user)) {
            log.info("Duplicate alias register action detected");
            return new ResponseEntity<Object>("Alias or Username already exists. Please choose a different alias.",
                    HttpStatus.NOT_ACCEPTABLE);
        } else if (user.getAvatar().length() >= customProperties.getAvatarMaxSize()) {
            log.info("Avatar is to large. Please choose a smaller avatar.");
            return new ResponseEntity<Object>("Avatar is to large. Please choose a smaller avatar.",
                    HttpStatus.NOT_ACCEPTABLE);
        } else if (authenticationService.register(user)) {
            log.info("New user register action successful for {}", user.getAlias());
            return new ResponseEntity<Object>(user, HttpStatus.CREATED);
        } else {
            log.info("Fatal error of unknown cause. Bad request.");
            return new ResponseEntity<Object>("Fatal error of unknown cause.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody User user) {
        authenticationService.update(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PatchMapping(path = "update/alias", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateAlias(@RequestHeader(name = "Authorization") String authenticationHeader,
                                            @RequestBody AliasDto alias) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (authenticationService.changeAlias(id, alias.getOldAlias(), alias.getNewAlias(), alias.getPassword())) {
            log.info("Alias change success for {} to {}.", alias.getOldAlias(), alias.getNewAlias());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Alias change fail for {} to {}.", alias.getOldAlias(), alias.getNewAlias());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PatchMapping(path = "update/email", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateEmail(@RequestHeader(name = "Authorization") String authenticationHeader,
                                            @RequestBody EmailDto email) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (authenticationService.changeEmail(id, email.getOldEmail(), email.getNewEmail(), email.getPassword())) {
            log.info("Email change success for {} to {}.", email.getOldEmail(), email.getNewEmail() );
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Email changed fail for {} to {}.", email.getOldEmail(), email.getNewEmail() );
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PatchMapping(path = "update/password", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updatePassword(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody PasswordDto password) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (authenticationService.changePassword(id, password.getOldPassword(),
                password.getNewPassword())) {
            log.info("Password update success for {},", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Password update fail for {}.", id);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path = "update/password/by/{email}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updatePasswordByEmail(@PathVariable("email") String email) {
        if (authenticationService.sendForgotPasswordLink(email)) {
            log.info("forgotten password email sent");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.info("forgotten password email not sent");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path = "update/verify", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateVerify(@PathVariable("email") String email) {
        if (authenticationService.sendForgotPasswordLink(email)) {
            log.info("forgotten password email sent");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.info("forgotten password email not sent");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "read", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationUser> read(@RequestHeader(name = "Authorization") String authenticationHeader) {
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            throw new AuthenticationException("token corrupt");
        } else {
            authenticationService.getAuthenticationUser(id);
            return new ResponseEntity<AuthenticationUser>(HttpStatus.OK);
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleParserException(AuthenticationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Authentication fail. " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Json conversion failed. " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParserException(ParseException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Please supply all required fields in register. " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}