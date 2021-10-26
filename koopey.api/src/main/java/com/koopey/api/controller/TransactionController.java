package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
import com.koopey.api.service.TransactionService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody Transaction transaction) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        transaction.setSellerId(id);
        transactionService.save(transaction);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Transaction transaction) {

        transactionService.delete(transaction);

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Transaction transaction) {

        transactionService.save(transaction);

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value = "read/{transactionId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Transaction> readTransaction(@PathVariable("transactionId") UUID transactionId) {

        Optional<Transaction> transaction = transactionService.findById(transactionId);

        if (transaction.isPresent()) {
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "search/by/buyer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> searchByBuyer(@RequestBody UUID userId) {

        List<User> users = transactionService.findBuyers(userId);

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/destination", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchByDestination(@RequestBody UUID locationId) {

        List<Location> locations = transactionService.findDestinations(locationId);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/seller", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> searchBySeller(@RequestBody UUID userId) {

        List<User> users = transactionService.findSellers(userId);

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/source", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Location>> searchBySource(@RequestBody UUID locationId) {

        List<Location> locations = transactionService.findSources(locationId);

        if (locations.isEmpty()) {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Location>>(locations, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "search/by/asset", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Asset>> searchByAsset(@RequestBody UUID assetId) {

        List<Asset> transactions = transactionService.findAssets(assetId);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Asset>>(transactions, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Asset>>(transactions, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> search(@RequestBody Transaction transaction) {

        List<Transaction> transactions = transactionService.findAll();

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.NO_CONTENT);
        }
    }
}
