package com.koopey.api.controller;


import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.LocationRepository;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("locations")
public class LocationController {
    
    private static Logger LOGGER = Logger.getLogger(LocationController.class.getName());

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping(value="create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Location location) { 
        LOGGER.log(Level.INFO, "create(" + location.getId() + ")");
        locationRepository.save(location);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Location location) {
        LOGGER.log(Level.INFO, "delete(" + location.getId() + ")");
        locationRepository.delete(location);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Location location) {
        LOGGER.log(Level.INFO, "delete(" + location.getId() + ")");      
        locationRepository.save(location);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    // @GetMapping(value= "read/my/locations/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
    //     MediaType.APPLICATION_JSON_VALUE })
    // public ResponseEntity<List<Location>> readMyLocations(@PathVariable("userId") String userId) {

    //     List<Location> locations = locationRepository.findById(userId);

    //     return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);     
    // }

    @GetMapping(value= "read/{locationId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> read(@PathVariable("locationId") UUID locationId) {

        Optional<Location> location = locationRepository.findById(locationId);

        if (location.isPresent()){
            return new ResponseEntity<Location> (location.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Location> (location.get(), HttpStatus.NOT_FOUND);
        }      
     
    }

    @PostMapping(value= "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> search(@RequestBody Location location) {

        List<Location> locations=  locationRepository.findAll();     

        return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
    }
}
