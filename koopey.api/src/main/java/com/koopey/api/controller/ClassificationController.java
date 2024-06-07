package com.koopey.api.controller;

import com.koopey.api.model.dto.ClassificationDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.model.parser.ClassificationParser;
import com.koopey.api.service.ClassificationService;
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
@RequestMapping("classification")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody ClassificationDto classificationDto) throws ParseException {

        Classification classification = ClassificationParser.convertToEntity(classificationDto);
        classification = classificationService.save(classification);
        return new ResponseEntity<UUID>(classification.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Classification classification) {
        classificationService.delete(classification);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
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

    @PostMapping(value = "search/by/tags", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> searchByTags(@RequestBody(required = true) List<Tag> tags) {

        List<Asset> assets = classificationService.findAssets(tags);

        if (assets.isEmpty()) {
            return new ResponseEntity<List<Asset>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Asset>>(assets, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/asset", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Tag>> searchByAsset(@RequestHeader(name = "Authorization") String authenticationHeader,
            @PathVariable UUID assetId) {

        List<Tag> tags = classificationService.findTags(assetId);

        if (tags.isEmpty()) {
            return new ResponseEntity<List<Tag>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        }
    }

}
