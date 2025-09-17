package com.koopey.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.model.parser.TagParser;
import com.koopey.api.model.type.TagType;
import com.koopey.api.service.TagService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;
    private TagParser tagParser;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody TagDto tagDto) throws ParseException {
        Tag tag = TagParser.toEntity(tagDto);
        tag = tagService.save(tag);
        return new ResponseEntity<UUID>(tag.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody TagDto tagDto) throws ParseException {
        Tag tag = TagParser.toEntity(tagDto);
        tagService.delete(tag);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "export", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> exportMany() throws ParseException, JsonProcessingException {
        String tags = tagParser.toString(tagService.findAll());
           return new ResponseEntity<byte[]>(tags.getBytes(), HttpStatus.CREATED);
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody TagDto tagDto) throws ParseException {
        Tag tag = TagParser.toEntity(tagDto);
        tagService.save(tag);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{tagId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Tag> read(@PathVariable("tagId") UUID tagId) {

        Optional<Tag> tag = tagService.findById(tagId);

        if (tag.isPresent()) {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "read/many", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Tag>> readMany(@RequestHeader(name = "Content-Language") String language) {

        List<Tag> tags = tagService.findAll(language);

        if (tags.isEmpty()) {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        }
    }

    @GetMapping("read/many/popular")
    public ResponseEntity<List<Tag>> popular() {
        List<Tag> tags = tagService.findByType(TagType.NORMAL.toString());
        return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
    }

    @GetMapping(value = "read/suggestions/{str}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Tag>> readSuggestions(@RequestHeader(name = "Content-Language") String language,
            @PathVariable("str") String str) {

        List<Tag> tags = tagService.findSuggestions(str, language);

        if (tags.isEmpty()) {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        }
    }

    @PostMapping(value = "import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> importMany(@RequestParam("file") MultipartFile file) throws ParseException, IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        List<Tag> tags = tagParser.toEntities(content);
        tagService.saveAll(tags);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("search")
    public ResponseEntity<List<Tag>> search(@RequestBody Tag tag) {

        List<Tag> tags = tagService.findAll();

        if (tags.isEmpty()) {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        }

    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleException(WebRequest request, IOException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleException(WebRequest request, JsonProcessingException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleException(WebRequest request, ParseException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}