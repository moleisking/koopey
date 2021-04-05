package com.koopey.server.controller;


import com.koopey.server.model.Review;
import com.koopey.server.repository.ReviewRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
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
@RequestMapping("reviews")
public class ReviewController {

    private static Logger LOGGER = Logger.getLogger(AssetController.class.getName());
    
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping(value= "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Review review) {
        LOGGER.log(Level.INFO, "create(" + review.getId() + ")");
        reviewRepository.save(review);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Review review) {
        LOGGER.log(Level.INFO, "delete(" + review.getId() + ")");
        reviewRepository.delete(review);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Review review) {
        LOGGER.log(Level.INFO, "delete(" + review.getId() + ")");      
        reviewRepository.save(review);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{reviewId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Review> read(@PathVariable("reviewId") UUID reviewId) {

        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            return new ResponseEntity<Review>(review.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Review>(review.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Review>> search(@RequestBody Review review) {
        return new ResponseEntity<List<Review>>(reviewRepository.findAll(), HttpStatus.OK);
    }
}