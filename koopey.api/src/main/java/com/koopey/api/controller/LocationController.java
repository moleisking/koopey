package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Location;
import com.koopey.api.service.GoogleService;
import com.koopey.api.service.LocationService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("location")
public class LocationController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private LocationService locationService;

    @Autowired
    private GoogleService googleService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody Location location) {
        locationService.save(location);
        return new ResponseEntity<UUID>(location.getId(),HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Location location) {
        locationService.delete(location);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Location location) {        
        location = locationService.save(location);        
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{locationId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> read(@PathVariable("locationId") UUID locationId) {

        Optional<Location> location = locationService.findById(locationId);

        if (location.isPresent()) {
            return new ResponseEntity<Location>(location.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Location>(location.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "search/by/range/kilometers", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByRangeInKilometers(@RequestBody SearchDto search) {

        List<Location> locations = locationService.findByAreaAsKilometer(search.getLatitude(), search.getLongitude(),
                search.getRadius());

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/by/range/miles", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByRangeInMiles(@RequestBody SearchDto search) {

        List<Location> locations = locationService.findByAreaAsMiles(search.getLatitude(), search.getLongitude(),
                search.getRadius());

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/place", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> searchForPlace(@RequestBody(required = true) Location location) {

        location = googleService.findPlaceSearch(location);

        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @PostMapping(value = "search/geocode", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> searchForGeocode(@RequestBody(required = true) Location location) {

        location = this.googleService.findGeocode(location);

        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> search(@RequestBody SearchDto search) {

        List<Location> locations = locationService.findAll();

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }
}
