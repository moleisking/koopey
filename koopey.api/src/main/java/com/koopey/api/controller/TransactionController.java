package com.koopey.api.controller;

import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.model.parser.TagParser;
import com.koopey.api.model.parser.TransactionParser;
import com.koopey.api.service.TransactionService;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
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
    public ResponseEntity<UUID> create(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody TransactionDto transactionDto) throws ParseException {

        Transaction transaction = TransactionParser.convertToEntity(transactionDto);

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (transactionService.isDuplicate(transaction)) {
            return new ResponseEntity<UUID>(HttpStatus.CONFLICT);
        } else if (transactionService.hasSellerOnly(transaction)) {
            transaction.setSellerId(id);
            transaction = transactionService.save(transaction);
            return new ResponseEntity<UUID>(transaction.getId(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<UUID>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody TransactionDto transactionDto) throws ParseException {

        Transaction transaction = TransactionParser.convertToEntity(transactionDto);
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long buyerCount = transactionService.countByBuyer(transaction);
        Long sellerCount = transactionService.countBySeller(transaction);

        if (transaction.getSellerId().equals(id) && buyerCount == 0 && sellerCount == 1) {
            transactionService.delete(transaction);
            return new ResponseEntity<String>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping(value = "read/{transactionId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<TransactionDto> read(@PathVariable("transactionId") UUID transactionId,
            @RequestParam(value = "children", required = false) Boolean children) {

        Optional<Transaction> transaction = transactionService.findById(transactionId);

        if (transaction.isPresent()) {
            if (children != null && children == true) {
                return new ResponseEntity<TransactionDto>(TransactionParser.convertToDto(transaction.get(), true),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<TransactionDto>(TransactionParser.convertToDto(transaction.get(), false),
                        HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<TransactionDto>(TransactionDto.builder().build(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> search(@RequestBody SearchDto search) {

        List<Transaction> transactions = transactionService.findAll();

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/between/dates", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> searchBetweenDates(
            @RequestHeader(name = "Authorization") String authenticationHeader, @RequestBody SearchDto search) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        // List<Transaction> transactions = transactionService.findBetweenDates(id,
        // search.getStart(), search.getEnd());

        List<Transaction> transactions = transactionService.findAll();

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/by/type/equal/quote", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<TransactionDto>> searchByTypeEqualQuote(@RequestBody SearchDto search) {

        List<TransactionDto> transactions = TransactionParser.convertToDtos(transactionService.findByQuote(search),
                true);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<TransactionDto>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<TransactionDto>>(transactions, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/asset/{assetId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> searchByAsset(@PathVariable UUID assetId) {

        List<Transaction> transactions = transactionService.findByAsset(assetId);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/buyer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<TransactionDto>> searchByBuyer(
            @RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam(value = "children", required = false) Boolean children) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Transaction> transactions = transactionService.findByBuyer(id);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<TransactionDto>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else if (children != null && children == true) {
            return new ResponseEntity<List<TransactionDto>>(TransactionParser.convertToDtos(transactions, true),
                    HttpStatus.OK);
        } else if (children != null && children == false) {
            return new ResponseEntity<List<TransactionDto>>(TransactionParser.convertToDtos(transactions, false),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<List<TransactionDto>>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "search/by/buyer/or/seller", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> searchByBuyerOrSeller(
            @RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Transaction> transactions = transactionService.findByBuyerOrSeller(id);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/destination/{locationId}", consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> searchByDestination(
            @RequestHeader(name = "Authorization") String authenticationHeader, @PathVariable UUID locationId) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Transaction> transactions = transactionService.findByDestination(id);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        }
    }

    @GetMapping(value = "search/by/seller", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<TransactionDto>> searchBySeller(
            @RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestParam(value = "children", required = false) Boolean children) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Transaction> transactions = transactionService.findBySeller(id);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<TransactionDto>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else if (children != null && children == true) {
            return new ResponseEntity<List<TransactionDto>>(TransactionParser.convertToDtos(transactions, true),
                    HttpStatus.OK);
        } else if (children != null && children == false) {
            return new ResponseEntity<List<TransactionDto>>(TransactionParser.convertToDtos(transactions, false),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<List<TransactionDto>>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "search/by/source/{locationId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> searchBySource(@PathVariable UUID locationId) {

        List<Transaction> transactions = transactionService.findBySource(locationId);

        if (transactions.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
        }
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody TransactionDto transactionDto) throws ParseException {

        Transaction transaction = TransactionParser.convertToEntity(transactionDto);
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        Long buyerCount = transactionService.countByBuyer(transaction);
        Long sellerCount = transactionService.countBySeller(transaction);

        if (!transaction.getSellerId().equals(id) && buyerCount == 0 && sellerCount == 1) {
            log.info("Buyer transaction");
            // Buyer
            transaction.setBuyerId(id);
            transactionService.save(transaction);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else if (transaction.getSellerId().equals(id) && buyerCount == 0 && sellerCount == 0) {
            log.info("Seller create transaction");
            // Seller Create
            transaction.setSellerId(id);
            transactionService.save(transaction);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else if (transaction.getSellerId().equals(id) && buyerCount == 0 && sellerCount == 1) {
            log.info("Seller edit transaction");
            // Seller Edit
            transaction.setSellerId(id);
            transactionService.save(transaction);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
