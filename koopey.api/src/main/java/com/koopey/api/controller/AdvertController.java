package com.koopey.api.controller;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.service.AdvertService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @PutMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UUID> create(@RequestBody Advert advert) {
        advert = advertService.save(advert);
        return new ResponseEntity<UUID>(HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Advert advert) {
        advertService.delete(advert);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Advert advert) {
        advertService.save(advert);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{advertId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Advert> read(@PathVariable("advertId") UUID advertId) {

        Optional<Advert> advert = advertService.findById(advertId);

        if (advert.isPresent()) {
            return new ResponseEntity<Advert>(advert.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Advert>(advert.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Advert>> search(@RequestBody Advert advert) {

        List<Advert> adverts = advertService.findAll();

        if (adverts.isEmpty()) {
            return new ResponseEntity<List<Advert>>(adverts, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Advert>>(adverts, HttpStatus.OK);
        }
    }

}