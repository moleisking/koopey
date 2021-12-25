package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.entity.Review;
import com.koopey.api.service.ReviewService;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody Review review) {
       review = reviewService.save(review);
        return new ResponseEntity<UUID>(HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody Review review) {
        reviewService.delete(review);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @GetMapping(value = "read/{reviewId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Review> read(@PathVariable("reviewId") UUID reviewId) {

        Optional<Review> review = reviewService.findById(reviewId);

        if (review.isPresent()) {
            return new ResponseEntity<Review>(review.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Review>(review.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "read/by/asset/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Review>> readByAsset(@PathVariable("assetId") UUID assetId) {

        List<Review> reviews = reviewService.findByAsset(assetId);

        if (reviews.isEmpty()) {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Review>> search(@RequestBody Review review) {

        List<Review> reviews = reviewService.findAll();
        if (reviews.isEmpty()) {

            return new ResponseEntity<List<Review>>(reviews, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/asset/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Review>> searchByAsset(@PathVariable("assetId") UUID assetId) {

        List<Review> reviews = reviewService.findByAsset(assetId);

        if (reviews.isEmpty()) {
            return new ResponseEntity<List<Review>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);

        }
    }

    @GetMapping(value = "search/by/buyer/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Review>> searchByBuyer(@PathVariable("userId") UUID userId) {

        List<Review> reviews = reviewService.findByBuyer(userId);

        if (reviews.isEmpty()) {
            return new ResponseEntity<List<Review>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);

        }
    }

    @GetMapping(value = "search/by/seller/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Review>> searchBySeller(@PathVariable("userId") UUID userId) {

        List<Review> reviews = reviewService.findBySeller(userId);

        if (reviews.isEmpty()) {
            return new ResponseEntity<List<Review>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);

        }
    }

    @GetMapping(path = "search/by/my/buyer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Review>> searchByMyBuyer(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Review>>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }

        List<Review> reviews = reviewService.findByBuyer(id);

        if (reviews.isEmpty()) {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
        }
    }

    @GetMapping(path = "search/by/my/seller", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Review>> searchByMySeller(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (id.toString().isEmpty()) {
            return new ResponseEntity<List<Review>>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }

        List<Review> reviews = reviewService.findBySeller(id);

        if (reviews.isEmpty()) {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
        }
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody Review review) {
        reviewService.save(review);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}