package com.koopey.api.controller;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.dto.UserDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.model.parser.UserParser;
import com.koopey.api.service.JwtService;
import com.koopey.api.service.UserService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private JwtService jwtTokenUtility;

    @Autowired
    private UserService userService;   

    @GetMapping(path = "read/{userId}", produces = "application/json")
    public ResponseEntity<UserDto> read(@PathVariable("userId") UUID userId) {
        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {
            return new ResponseEntity<UserDto>(UserParser.toDto(user.get()), HttpStatus.OK);
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

    @PatchMapping("/update/cookie/{cookie}")
    public ResponseEntity<Void> updateCookie(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("cookie") Boolean cookie) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
        } else         if (userService.updateGdpr(id, cookie)) {
            return new ResponseEntity<Void>( HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/currency/{currency}")
    public ResponseEntity<Void> updateCurrency(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("currency") String currency) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
        }else if (userService.updateCurrency(id, currency)) {
            return new ResponseEntity<Void>( HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/language/{language}")
    public ResponseEntity<Void> updateLanguage(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("language") String language) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
        }else         if (userService.updateLanguage(id, language)) {
            return new ResponseEntity<Void>( HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/gdpr/{gdpr}")
    public ResponseEntity<Void> updateGdpr(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("gdpr") Boolean gdpr) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
        } else if (userService.updateGdpr(id, gdpr)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/measure/{measure}")
    public ResponseEntity<Void> updateMeasure(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("measure") String measure) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
        }else if (userService.updateMeasure(id, measure)) {
            return new ResponseEntity<Void>( HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/track/{track}")
    public ResponseEntity<Void> updateTrack(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("track") Boolean track) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }else  if (userService.updateTrack(id, track)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/notify/by/device/{device}")
    public ResponseEntity<Void> updateNotifyByDevice(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("device") Boolean device) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }else  if (userService.updateNotifyByDevice(id, device)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/notify/by/email/{email}")
    public ResponseEntity<Void> updateNotifyByEmail(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("email") Boolean email) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }else  if (userService.updateNotifyByEmail(id, email)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

     @PatchMapping("/update/location")
    public ResponseEntity<Void> updateLocation(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam("altitude") BigDecimal altitude, @RequestParam("latitude") BigDecimal latitude, @RequestParam("longitude}") BigDecimal longitude) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else if (userService.updateLocation(id,altitude , latitude, longitude)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

     @PatchMapping("/update/term/{term}")
    public ResponseEntity<Void> updateTerm(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("term") Boolean term) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
        }else   if (userService.updateTerm(id, term)) {
            return new ResponseEntity<Void>( HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}