package com.koopey.server.controller;

import java.util.List;
import java.util.Optional;

import com.koopey.server.data.AssetRepository;
import com.koopey.server.model.Asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("assets")
public class AssetController {

    @Autowired
    private AssetRepository assetRepository;

    @PostMapping("create")
    public ResponseEntity<Void> putUser(@RequestBody Asset asset) {
        System.out.println("putAsset");
        assetRepository.save(asset);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{assetId}")
    public ResponseEntity<Asset> getAsset(@PathVariable("assetId") String assetId) {

        Optional<Asset> asset = assetRepository.findById(assetId);

        if (asset.isPresent()) {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Asset>> getAssets() {

        return new ResponseEntity<List<Asset>>(assetRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("ping")
    public String getPing() {

        return "Hello world!";
    }
}