package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.service.AssetService;
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

@RestController
@RequestMapping("asset")
public class AssetController { 

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private AssetService assetService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Asset asset) {       
        assetService.save(asset);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Asset asset) {       
        assetService.delete(asset);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value= "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Asset asset) {     
        assetService.save(asset);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Asset> read(@PathVariable("assetId") UUID assetId) {

        Optional<Asset> asset = assetService.findById(assetId);

        if (asset.isPresent()) {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "read/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> readMyAssets(@RequestHeader(name = "Authorization") String authenticationHeader) {    
       
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
       
        if (id.toString().isEmpty()) {
            return new ResponseEntity<Object>("Corrupt token.", HttpStatus.BAD_REQUEST);
        } else {

            Optional<Asset> asset = assetService.findById(id);
        
            if (asset.isPresent()){
                return new ResponseEntity<Object>(asset.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
            }            
        }
    }

    @PostMapping(value="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> search(@RequestBody Asset asset) {

        List<Asset> assets= assetService.findAll();     

        if (assets.isEmpty()) {
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);           
        }    
    }

}