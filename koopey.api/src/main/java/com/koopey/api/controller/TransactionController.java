package com.koopey.api.controller;

import com.koopey.api.model.Transaction;
import com.koopey.api.repository.TransactionRepository;

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
@RequestMapping("transaction")
public class TransactionController {

    private static Logger LOGGER = Logger.getLogger(TransactionController.class.getName());

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping(value= "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Transaction transaction) {
        LOGGER.log(Level.INFO, "create(" + transaction.getId() + ")");
        transactionRepository.save(transaction);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Transaction transaction) {
        LOGGER.log(Level.INFO, "delete(" + transaction.getId() + ")");
        transactionRepository.delete(transaction);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value= "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Transaction transaction) {
        LOGGER.log(Level.INFO, "delete(" + transaction.getId() + ")");      
        transactionRepository.save(transaction);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value = "read/{transactionId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Transaction> read(@PathVariable("transactionId") UUID transactionId) {

        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (transaction.isPresent()) {
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value ="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> search(@RequestBody Transaction transaction) {

        List<Transaction> transactions=  transactionRepository.findAll();     

        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }
}