package com.koopey.api.controller;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.koopey.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.dto.WalletDto;
import com.koopey.api.model.entity.Wallet;
import com.koopey.api.model.parser.WalletParser;
import com.koopey.api.service.WalletService;

@RestController
@RequestMapping("wallet")
public class WalletController {
    @Autowired
    private JwtService jwtTokenUtility;
    
    @Autowired
    private WalletService walletService;

    private WalletParser walletParser = new WalletParser();

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody WalletDto walletDto) throws ParseException {
             
        Wallet wallet = walletParser.convertToEntity(walletDto);

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        wallet.setOwnerId(id);
        wallet= walletService.save(wallet);
        return new ResponseEntity<UUID>(wallet.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestHeader(name = "Authorization") String authenticationHeader,
            @RequestBody WalletDto walletDto) throws ParseException {

        Wallet wallet = walletParser.convertToEntity(walletDto);
        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);

        if (wallet.getOwnerId().equals(id) ) {
            walletService.delete(wallet);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>( HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping(value = "read/{walletId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<WalletDto> read(@PathVariable("walletId") UUID walletId,
            @RequestParam(value = "children", required = false) Boolean children) {

        Optional<Wallet> wallet = walletService.findById(walletId);

        if (wallet.isPresent()) {
            return new ResponseEntity<WalletDto>(walletParser.convertToDto(wallet.get()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<WalletDto>(WalletDto.builder().build(), HttpStatus.NOT_FOUND);
        }

    }
   
    @GetMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Wallet>> search(@RequestHeader(name = "Authorization") String authenticationHeader) {

        UUID id = jwtTokenUtility.getIdFromAuthenticationHeader(authenticationHeader);
        List<Wallet> wallets = walletService.findByOwner(id);

        if (wallets.isEmpty()) {
            return new ResponseEntity<List<Wallet>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Wallet>>(wallets, HttpStatus.OK);
        }
    }
}
