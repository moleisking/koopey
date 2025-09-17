package com.koopey.api.controller;

import com.koopey.api.exception.JwtException;
import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.parser.AssetParser;
import com.koopey.api.service.AssetService;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.koopey.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("asset")
public class AssetController {

    @Autowired
    private JwtService jwtTokenUtility;

    @Autowired
    private AssetService assetService;
   
    private AssetParser assetParser = new AssetParser();

    @GetMapping(value = "count", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> count() {
        Long count = assetService.count();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody AssetDto assetDto) throws ParseException {

        Asset asset = assetParser.toEntity(assetDto);

        if (assetService.isDuplicate(asset)) {
            return new ResponseEntity<UUID>(HttpStatus.CONFLICT);
        } else {
            asset = assetService.save(asset);
            return new ResponseEntity<UUID>(asset.getId(), HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody AssetDto assetDto) throws ParseException {
        Asset asset = assetParser.toEntity(assetDto);
        assetService.delete(asset);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Asset> read(@PathVariable("assetId") UUID assetId) {

        Optional<Asset> asset = assetService.findById(assetId);

        if (asset.isPresent()) {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> search(@RequestBody SearchDto search) {

        log.debug("asset/search");
        List<Asset> assets = assetService.findAll();              

        if (assets.isEmpty()) {
            log.debug("asset/search", HttpStatus.NO_CONTENT.toString());
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.NO_CONTENT);
        } else {
            log.debug("asset/search", HttpStatus.OK.toString());
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
        }
    }

    @GetMapping(path = "search/by/buyer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asset>> searchByBuyer(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Asset>>(HttpStatus.BAD_REQUEST);
        } else {
            List<Asset> assets = assetService.findBySeller(id);
            if (assets.isEmpty()) {
                return new ResponseEntity<List<Asset>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
            }
        }
    }

    @GetMapping(path = "search/by/seller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asset>> searchBySeller(
            @RequestHeader(name = "Authorization") String authenticationHeader) {


        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Asset>>(HttpStatus.BAD_REQUEST);
        } else {
            List<Asset> assets = assetService.findByBuyer(id);
            if (assets.isEmpty()) {
                return new ResponseEntity<List<Asset>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
            }
        }
    }

    @GetMapping(path = "search/by/buyer/or/seller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asset>> searchByUser(
            @RequestHeader(name = "Authorization") String authenticationHeader) throws JwtException, AccessDeniedException {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Asset>>(HttpStatus.BAD_REQUEST);
        } else {
            List<Asset> assets = assetService.findByBuyerOrSeller(id);
            if (assets.isEmpty()) {
                return new ResponseEntity<List<Asset>>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
            }
        }
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody AssetDto assetDto) throws ParseException {
        Asset asset = assetParser.toEntity(assetDto);
        assetService.save(asset);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PatchMapping("/update/available/{available}")
    public ResponseEntity<Void> updateAvailable(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable("available") Boolean available) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else if (assetService.updateAvailable(id, available)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }




}