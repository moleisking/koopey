package com.koopey.api.controller;

import com.koopey.api.model.entity.Tag;
import com.koopey.api.model.type.TagType;
import com.koopey.api.service.TagService;

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

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins = "http://localhost:1709", maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping(value= "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Tag tag) {     
        tagService.save(tag);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Tag tag) {     
        tagService.delete(tag);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Tag tag) {     
        tagService.save(tag);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{tagId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Tag> read(@PathVariable("tagId") UUID tagId) {

        Optional<Tag> tag = tagService.findById(tagId);

        if (tag.isPresent()) {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("popular")
    public ResponseEntity<List<Tag>> popular() {
        return new ResponseEntity<List<Tag>>(tagService.findByType(TagType.NORMAL), HttpStatus.OK);
    }

    @PostMapping("search")
    public ResponseEntity<List<Tag>> search(@RequestBody Tag tag) {
        return new ResponseEntity<List<Tag>>(tagService.findAll(), HttpStatus.OK);
    }
}