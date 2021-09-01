package com.koopey.api.controller;

import com.koopey.api.model.entity.Game;
import com.koopey.api.service.GameService;

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
@RequestMapping("game")
public class GameController {  

    @Autowired
    private GameService gameService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Game game) {
       
        gameService.save(game);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Game game) {
       
        gameService.delete(game);
      
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value= "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Game game) {
        
        gameService.save(game);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{gameId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> read(@PathVariable("gameId") UUID gameId) {

        Optional<Game> game = gameService.findById(gameId);

        if (game.isPresent()) {
            return new ResponseEntity<Game>(game.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Game>(game.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value="read/me", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> readMyGames(@PathVariable("gameId") UUID gameId) {

        Optional<Game> game = gameService.findById(gameId);

        if (game.isPresent()) {
            return new ResponseEntity<Game>(game.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Game>(game.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Game>> search(@RequestBody Game game) {
        List<Game> games= gameService.findAll();     

        if (games.isEmpty()) {
            return new ResponseEntity<List<Game>>(games, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Game>>(games, HttpStatus.OK);           
        }
    }
}
