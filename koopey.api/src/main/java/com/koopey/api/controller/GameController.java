package com.koopey.api.controller;

import com.koopey.api.model.Game;
import com.koopey.api.repository.GameRepository;

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
@RequestMapping("games")
public class GameController {
    private static Logger LOGGER = Logger.getLogger(GameController.class.getName());

    @Autowired
    private GameRepository gameRepository;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Game game) {
        LOGGER.log(Level.INFO, "create(" + game.getId() + ")");
        gameRepository.save(game);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Game game) {
        LOGGER.log(Level.INFO, "delete(" + game.getId() + ")");
        gameRepository.delete(game);

        // check if image and reviews deleted
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value= "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Game game) {
        LOGGER.log(Level.INFO, "delete(" + game.getId() + ")");      
        gameRepository.save(game);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value="read/{gameId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> read(@PathVariable("gameId") UUID gameId) {

        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            return new ResponseEntity<Game>(game.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Game>(game.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Game>> search(@RequestBody Game game) {
        return new ResponseEntity<List<Game>>(gameRepository.findAll(), HttpStatus.OK);
    }
}
