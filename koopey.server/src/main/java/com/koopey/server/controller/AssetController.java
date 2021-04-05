package com.koopey.server.controller;

import com.koopey.server.model.Asset;
import com.koopey.server.repository.AssetRepository;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("assets")
public class AssetController {

    private static Logger LOGGER = Logger.getLogger(AssetController.class.getName());

    @Autowired
    private AssetRepository assetRepository;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Asset asset) {
        LOGGER.log(Level.INFO, "create(" + asset.getId() + ")");
        assetRepository.save(asset);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Asset asset) {
        LOGGER.log(Level.INFO, "delete(" + asset.getId() + ")");
        assetRepository.delete(asset);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value= "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Asset asset) {
        LOGGER.log(Level.INFO, "delete(" + asset.getId() + ")");      
        assetRepository.save(asset);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Asset> read(@PathVariable("assetId") UUID assetId) {

        Optional<Asset> asset = assetRepository.findById(assetId);

        if (asset.isPresent()) {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> search(@RequestBody Asset asset) {
        return new ResponseEntity<List<Asset>>(assetRepository.findAll(), HttpStatus.OK);
    }

}