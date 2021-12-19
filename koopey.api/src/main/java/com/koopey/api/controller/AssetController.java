package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.parser.AssetParser;
import com.koopey.api.service.AssetService;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("asset")
public class AssetController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private AssetService assetService;

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

        Asset asset = AssetParser.convertToEntity(assetDto);

        log.info(asset.toString());
        if (assetService.isDuplicate(asset)) {
            return new ResponseEntity<UUID>(HttpStatus.CONFLICT);
        } else {

            asset = assetService.save(asset);
            return new ResponseEntity<UUID>(asset.getId(), HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Asset asset) {
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
    public ResponseEntity<List<Asset>> search(@RequestBody Asset asset) {

        List<Asset> assets = assetService.findAll();

        if (assets.isEmpty()) {
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.NO_CONTENT);
        } else {
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
                return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Asset>>(HttpStatus.NOT_FOUND);
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
                return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Asset>>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping(path = "search/by/buyer/or/seller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asset>> searchByUser(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Asset>>(HttpStatus.BAD_REQUEST);
        } else {

            List<Asset> assets = assetService.findByBuyerOrSeller(id);

            if (assets.isEmpty()) {
                return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Asset>>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Asset asset) {
        assetService.save(asset);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/update/available/{available}")
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