package com.koopey.server.controller;

import com.koopey.server.data.AssetRepository;
import com.koopey.server.model.Asset;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Asset asset) {
        LOGGER.log(Level.INFO, "create(" + asset.getId() + ")");
        assetRepository.save(asset);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Asset asset) {
        LOGGER.log(Level.INFO, "delete(" + asset.getId() + ")");
        assetRepository.delete(asset);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Asset asset) {
        LOGGER.log(Level.INFO, "delete(" + asset.getId() + ")");      
        assetRepository.save(asset);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping("read/{assetId}")
    public ResponseEntity<Asset> read(@PathVariable("assetId") String assetId) {

        Optional<Asset> asset = assetRepository.findById(assetId);

        if (asset.isPresent()) {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Asset>(asset.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("search")
    public ResponseEntity<List<Asset>> search(@RequestBody Asset asset) {
        return new ResponseEntity<List<Asset>>(assetRepository.findAll(), HttpStatus.OK);
    }

}