package com.koopey.api.controller;

import com.koopey.api.model.dto.GameDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.parser.AssetParser;
import com.koopey.api.model.parser.GameParser;
import com.koopey.api.service.GameService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@RequestBody GameDto gameDto) throws ParseException {
        Game game = GameParser.convertToEntity(gameDto);
        game = gameService.save(game);
        return new ResponseEntity<UUID>(game.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@RequestBody GameDto gameDto) throws ParseException {
        Game game = GameParser.convertToEntity(gameDto);
        gameService.delete(game);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> update(@RequestBody GameDto gameDto) throws ParseException {
        Game game = GameParser.convertToEntity(gameDto);
        gameService.save(game);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "read/{gameId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> read(@PathVariable("gameId") UUID gameId) {

        Optional<Game> game = gameService.findById(gameId);

        if (game.isPresent()) {
            return new ResponseEntity<Game>(game.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Game>(game.get(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "read/me", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> readMyGames(@PathVariable("gameId") UUID gameId) {

        Optional<Game> game = gameService.findById(gameId);

        if (game.isPresent()) {
            return new ResponseEntity<Game>(game.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Game>(game.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Game>> search(@RequestBody Game game) {
        List<Game> games = gameService.findAll();

        if (games.isEmpty()) {
            return new ResponseEntity<List<Game>>(games, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
        }
    }
}
