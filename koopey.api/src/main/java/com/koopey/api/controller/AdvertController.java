package com.koopey.api.controller;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.repository.AdvertRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
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
@RequestMapping("adverts")
public class AdvertController {

    private static Logger LOGGER = Logger.getLogger(AdvertController.class.getName());

    @Autowired
    private AdvertRepository advertRepository;

    @PutMapping(value ="create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> create(@RequestBody Advert advert) {
        LOGGER.log(Level.INFO, "create(" + advert.getId() + ")");
        advertRepository.save(advert);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @PostMapping(value ="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Advert advert) {
        LOGGER.log(Level.INFO, "delete(" + advert.getId() + ")");
        advertRepository.delete(advert);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value ="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Advert advert) {
        LOGGER.log(Level.INFO, "delete(" + advert.getId() + ")");      
        advertRepository.save(advert);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value ="read/{advertId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Advert> read(@PathVariable("advertId") UUID advertId) {

        Optional<Advert> advert = advertRepository.findById(advertId);

        if (advert.isPresent()){
            return new ResponseEntity<Advert> (advert.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Advert> (advert.get(), HttpStatus.NOT_FOUND);
        }      
    }

    @PostMapping(value ="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Advert>> search(@RequestBody Advert advert) {
        return new ResponseEntity<List<Advert>>(advertRepository.findAll(), HttpStatus.OK);
    }

}