package com.koopey.server.controller;

import java.util.List;
import java.util.Optional;

import com.koopey.server.data.TagRepository;
import com.koopey.server.model.Tag;

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
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("create")
    public ResponseEntity<Void> putUser(@RequestBody Tag tag) {
        System.out.println("putTag");
        tagRepository.save(tag);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{tagId}")
    public ResponseEntity<Tag> getTag(@PathVariable("tagId") String tagId) {

        Optional<Tag> tag = tagRepository.findById(tagId);

        if (tag.isPresent()) {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Tag>(tag.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Tag>> getTags() {

        return new ResponseEntity<List<Tag>>(tagRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("ping")
    public String getPing() {

        return "Hello world!";
    }
}