package com.koopey.server.controller;

import java.util.List;
import java.util.Optional;

import com.koopey.server.data.ReviewRepository;
import com.koopey.server.model.Review;

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
@RequestMapping("reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("create")
    public ResponseEntity<Void> putUser(@RequestBody Review review) {
        System.out.println("putReview");
        reviewRepository.save(review);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable("reviewId") String reviewId) {

        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            return new ResponseEntity<Review>(review.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Review>(review.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Review>> getReviews() {

        return new ResponseEntity<List<Review>>(reviewRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("ping")
    public String getPing() {

        return "Hello world!";
    }
}