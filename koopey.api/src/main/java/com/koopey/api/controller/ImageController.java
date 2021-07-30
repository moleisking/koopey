package com.koopey.api.controller;

import com.koopey.api.model.entity.Image;
import com.koopey.api.repository.ImageRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:1709", maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("images")
public class ImageController {

    private static Logger LOGGER = Logger.getLogger(TagController.class.getName());

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping(value= "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Image image) {
        LOGGER.log(Level.INFO, "create(" + image.getId() + ")");
        imageRepository.save(image);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Image image) {
        LOGGER.log(Level.INFO, "delete(" + image.getId() + ")");
        imageRepository.delete(image);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Image image) {
        LOGGER.log(Level.INFO, "delete(" + image.getId() + ")");      
        imageRepository.save(image);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{tagId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Image> read(@PathVariable("imageId") UUID imageId) {

        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isPresent()) {
            return new ResponseEntity<Image>(image.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Image>(image.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("search")
    public ResponseEntity<List<Image>> search(@RequestBody Image image) {
        return new ResponseEntity<List<Image>>(imageRepository.findAll(), HttpStatus.OK);
    }
}