package com.koopey.server.controller;

import com.koopey.server.data.ReviewRepository;
import com.koopey.server.model.Review;
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
@RequestMapping("reviews")
public class ReviewController {

    private static Logger LOGGER = Logger.getLogger(AssetController.class.getName());
    
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Review review) {
        LOGGER.log(Level.INFO, "create(" + review.getId() + ")");
        reviewRepository.save(review);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Review review) {
        LOGGER.log(Level.INFO, "delete(" + review.getId() + ")");
        reviewRepository.delete(review);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Review review) {
        LOGGER.log(Level.INFO, "delete(" + review.getId() + ")");      
        reviewRepository.save(review);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping("read/{reviewId}")
    public ResponseEntity<Review> read(@PathVariable("reviewId") String reviewId) {

        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            return new ResponseEntity<Review>(review.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Review>(review.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("search")
    public ResponseEntity<List<Review>> search(@RequestBody Review review) {
        return new ResponseEntity<List<Review>>(reviewRepository.findAll(), HttpStatus.OK);
    }
}