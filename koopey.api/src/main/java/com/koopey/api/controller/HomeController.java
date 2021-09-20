package com.koopey.api.controller;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.dto.ContactDto;
import com.koopey.api.service.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private SmtpService smtpService;

    @PostMapping(value = "contact", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> contact(@RequestBody ContactDto contact) {

        smtpService.sendSimpleMessage(customProperties.getEmailAddress(), contact.email, contact.subject,
                contact.content);

        return new ResponseEntity<String>("Sent", HttpStatus.OK);
    }

}
