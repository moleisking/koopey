package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Journey;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
import com.koopey.api.service.JourneyService;
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
@RequestMapping("journey")
public class JourneyController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private JourneyService journeyService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody Journey journey) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

         journey.setPassangerId(id);
        journeyService.save(journey);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Journey journey) {

        journeyService.delete(journey);

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Journey journey) {

        journeyService.save(journey);

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value = "read/asset/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Journey>> readAsset(@PathVariable("assetId") UUID assetId) {

        List<Journey> assets = journeyService.findVehicle(assetId);

        if (assets.isEmpty()) {
            return new ResponseEntity<List<Journey>>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Journey>>(assets, HttpStatus.OK);
        }
    }

    @GetMapping(value = "read/journey/{journeyId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Journey> readJourney(@PathVariable("journeyId") UUID journeyId) {

        Optional<Journey> journey = journeyService.findById(journeyId);

        if (journey.isPresent()) {
            return new ResponseEntity<Journey>(journey.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Journey>(journey.get(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "search/by/vehicle", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> searchByVehicle(@RequestBody UUID assetId) {

        List<Asset> journeys = journeyService.findVehicles(assetId);

        if (journeys.isEmpty()) {
            return new ResponseEntity<List<Asset>>(journeys, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Asset>>(journeys, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/location", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByLocation(@RequestBody UUID locationId) {

        List<Location> locations = journeyService.findLocations(locationId);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/driver", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> searchByDriver(@RequestBody UUID userId) {

        List<User> users = journeyService.findDrivers(userId);

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/passanger", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> searchByPassanger(@RequestBody UUID userId) {

        List<User> users = journeyService.findPassangers(userId);

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Journey>> search(@RequestBody Journey journey) {

        List<Journey> journeys = journeyService.findAll();

        if (journeys.isEmpty()) {
            return new ResponseEntity<List<Journey>>(journeys, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Journey>>(journeys, HttpStatus.NO_CONTENT);
        }
    }
}
