package com.koopey.server.controller;

import com.koopey.server.data.TransactionRepository;
import com.koopey.server.model.Transaction;
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
@RequestMapping("transaction")
public class TransactionController {

    private static Logger LOGGER = Logger.getLogger(TransactionController.class.getName());

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Transaction transaction) {
        LOGGER.log(Level.INFO, "create(" + transaction.getId() + ")");
        transactionRepository.save(transaction);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Transaction transaction) {
        LOGGER.log(Level.INFO, "delete(" + transaction.getId() + ")");
        transactionRepository.delete(transaction);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Transaction transaction) {
        LOGGER.log(Level.INFO, "delete(" + transaction.getId() + ")");      
        transactionRepository.save(transaction);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping("read/{transactionId}")
    public ResponseEntity<Transaction> read(@PathVariable("transactionId") String transactionId) {

        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (transaction.isPresent()) {
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("search")
    public ResponseEntity<List<Transaction>> search(@RequestBody Transaction transaction) {

        List<Transaction> transactions=  transactionRepository.findAll();     

        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }
}