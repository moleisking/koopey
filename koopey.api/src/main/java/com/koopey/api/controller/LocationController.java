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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("location")
public class LocationController {

    @Autowired
    private GoogleService googleService;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody Location location) {
        if (location.getId() != null && locationService.exists(location.getId())) {
            return new ResponseEntity<UUID>(HttpStatus.CONFLICT);
        } else {
            locationService.save(location);
            return new ResponseEntity<UUID>(location.getId(), HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Location location) {
        locationService.delete(location);
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

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> search(@RequestBody SearchDto search) {

        List<Location> locations = locationService.findAll();

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/buyer/and/destination", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByBuyerAndDestination(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Location> locations = locationService.findByBuyerAndDestination(id);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/buyer/and/source", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByBuyerAndSource(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Location> locations = locationService.findByBuyerAndSource(id);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/by/geocode", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> searchByGeocode(@RequestBody(required = true) Location location) {
        location = this.googleService.findGeocode(location);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @PostMapping(value = "search/by/place", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> searchByPlace(@RequestBody(required = true) Location location) {
        location = googleService.findPlaceSearch(location);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @PostMapping(value = "search/by/range/in/kilometers", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
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

    @PostMapping(value = "search/by/range/in/miles", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
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

    @GetMapping(value = "search/by/destination/and/seller", consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByDestinationAndSeller(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Location> locations = locationService.findByDestinationAndSeller(id);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/seller/and/source", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchBySellerAndSource(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Location> locations = locationService.findBySellerAndSource(id);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Location location) {
        location = locationService.save(location);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
