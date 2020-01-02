package com.koopey.server.controller;

import com.koopey.server.data.TagRepository;
import com.koopey.server.model.Tag;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8889", maxAge = 3600)
@RestController
@RequestMapping("tags")
public class TagController {

    private static Logger LOGGER = Logger.getLogger(TagController.class.getName());

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Tag tag) {
        LOGGER.log(Level.INFO, "create(" + tag.getId() + ")");
        tagRepository.save(tag);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Tag tag) {
        LOGGER.log(Level.INFO, "delete(" + tag.getId() + ")");
        tagRepository.delete(tag);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Tag tag) {
        LOGGER.log(Level.INFO, "delete(" + tag.getId() + ")");      
        tagRepository.save(tag);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping("read/{tagId}")
    public ResponseEntity<Tag> read(@PathVariable("tagId") String tagId) {

        Optional<Tag> tag = tagRepository.findById(tagId);

        if (tag.isPresent()) {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("search")
    public ResponseEntity<List<Tag>> search(@RequestBody Tag tag) {
        return new ResponseEntity<List<Tag>>(tagRepository.findAll(), HttpStatus.OK);
    }
}