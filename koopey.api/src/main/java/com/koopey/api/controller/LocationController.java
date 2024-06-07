package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.dto.LocationDto;
import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.parser.AssetParser;
import com.koopey.api.model.parser.GameParser;
import com.koopey.api.model.parser.LocationParser;
import com.koopey.api.service.GoogleService;
import com.koopey.api.service.LocationService;

import java.text.ParseException;
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
@RequestMapping("location")
public class LocationController {

    @Autowired
    private GoogleService googleService;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private LocationService locationService;

    private final LocationParser locationParser =  new LocationParser();

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody LocationDto locationDto) throws ParseException {
        Location location = locationParser.convertToEntity(locationDto);
        if (locationService.isDuplicate(location)) {
            return new ResponseEntity<UUID>(HttpStatus.CONFLICT);
        } else {
            locationService.save(location);
            return new ResponseEntity<UUID>(location.getId(), HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody LocationDto locationDto) throws ParseException {
        Location location = locationParser.convertToEntity(locationDto);
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
            return new ResponseEntity<Location>(new Location(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> search(@RequestBody SearchDto search) {

        List<Location> locations = locationService.findAll();

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/by/geocode", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> searchByGeocode(@RequestBody(required = true) LocationDto locationDto)
            throws ParseException {
        Location location = locationParser.convertToEntity(locationDto);
        location = this.googleService.findGeocode(location);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @PostMapping(value = "search/by/place", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Location> searchByPlace(@RequestBody(required = true) LocationDto locationDto)
            throws ParseException {
        Location location = locationParser.convertToEntity(locationDto);
        location = googleService.findPlaceSearch(location);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @GetMapping(value = "search/by/owner", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByOwner(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Location> locations = locationService.findByOwner(id);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/by/range/in/kilometers", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByRangeInKilometers(@RequestBody SearchDto search) {

        List<Location> locations = locationService.findByAreaAsKilometer(search.getLatitude(), search.getLongitude(),
                search.getRadius());

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<List<Location>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        }
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Location location) {
        location = locationService.save(location);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
