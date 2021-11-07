package com.koopey.api.controller;

import com.koopey.api.model.entity.Image;
import com.koopey.api.service.ImageService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value= "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody Image image) {      
       image = imageService.save(image);
        return new ResponseEntity<UUID>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Image image) {       
        imageService.delete(image);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Image image) {        
        imageService.save(image);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @GetMapping(value="read/{tagId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Image> read(@PathVariable("imageId") UUID imageId) {

        Optional<Image> image = imageService.findById(imageId);

        if (image.isPresent()) {
            return new ResponseEntity<Image>(image.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Image>(image.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("search")
    public ResponseEntity<List<Image>> search(@RequestBody Image image) {
       
        List<Image> images= imageService.findAll();     

        if (images.isEmpty()) {
            return new ResponseEntity<List<Image>>(images, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Image>>(images, HttpStatus.OK);            
        }
    }
}