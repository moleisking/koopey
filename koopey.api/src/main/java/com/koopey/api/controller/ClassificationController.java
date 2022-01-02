package com.koopey.api.controller;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.service.ClassificationService;
import java.util.ArrayList;
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
@RequestMapping("classification")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody Classification classification) {
        classification = classificationService.save(classification);
        return new ResponseEntity<UUID>(classification.getId(), HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Classification classification) {
        classificationService.delete(classification);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Classification classification) {
        classificationService.save(classification);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{classificationId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Classification> read(@PathVariable("classificationId") UUID classificationId) {

        Optional<Classification> classification = classificationService.findById(classificationId);

        if (classification.isPresent()) {
            return new ResponseEntity<Classification>(classification.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Classification>(classification.get(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "search/assets", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> searchAssets(@RequestBody(required = true) List<Tag> tags) {

        List<Asset> assets = new ArrayList(); // = classificationService.findAssets(tags);

        if (assets.isEmpty()) {
            return new ResponseEntity<List<Asset>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/tags", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Tag>> searchTags(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable UUID assetId) {

        List<Tag> tags = classificationService.findTags(assetId);

        if (tags.isEmpty()) {
            return new ResponseEntity<List<Tag>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        }
    }

}
