package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.dto.UserDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.parser.UserParser;
import com.koopey.api.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private UserService userService;

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody User user) {
        userService.delete(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(path = "read/{userId}", produces = "application/json")
    public ResponseEntity<UserDto> read(@PathVariable("userId") UUID userId) {
        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {
            return new ResponseEntity<UserDto>(UserParser.convertToDto(user.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "read/me", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> readMyUser(@RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> search(@RequestBody SearchDto search) {

        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/by/listener", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> searchByListener(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<User> users = userService.findListeners(id);

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/update/cookie/{cookie}")
    public ResponseEntity<Object> updateCookie(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("cookie") Boolean cookie) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Fatal error. Token corrupt.", HttpStatus.BAD_REQUEST);
        }

        if (userService.updateGdpr(id, cookie)) {
            return new ResponseEntity<Object>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/currency/{currency}")
    public ResponseEntity<Object> updateCurrency(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("currency") String currency) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Fatal error. Token corrupt.", HttpStatus.BAD_REQUEST);
        }

        if (userService.updateCurrency(id, currency)) {
            return new ResponseEntity<Object>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/language/{language}")
    public ResponseEntity<Object> updateLanguage(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("language") String language) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Fatal error. Token corrupt.", HttpStatus.BAD_REQUEST);
        }

        if (userService.updateLanguage(id, language)) {
            return new ResponseEntity<Object>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/gdpr/{gdpr}")
    public ResponseEntity<Object> updateGdpr(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("gdpr") Boolean gdpr) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Fatal error. Token corrupt.", HttpStatus.BAD_REQUEST);
        }

        if (userService.updateGdpr(id, gdpr)) {
            return new ResponseEntity<Object>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/measure/{measure}")
    public ResponseEntity<Object> updateMeasure(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("measure") String measure) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Fatal error. Token corrupt.", HttpStatus.BAD_REQUEST);
        }

        if (userService.updateMeasure(id, measure)) {
            return new ResponseEntity<Object>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/track/{track}")
    public ResponseEntity<Void> updateTrack(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("track") Boolean track) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (userService.updateTrack(id, track)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/notify/by/device/{device}")
    public ResponseEntity<Void> updateNotifyByDevice(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("device") Boolean device) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (userService.updateNotifyByDevice(id, device)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/notify/by/email/{email}")
    public ResponseEntity<Void> updateNotifyByEmail(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("email") Boolean email) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (userService.updateNotifyByEmail(id, email)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}