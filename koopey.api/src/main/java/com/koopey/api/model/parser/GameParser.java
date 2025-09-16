package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.GameDto;
import com.koopey.api.model.entity.Game;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.koopey.api.model.entity.Location;
import com.koopey.api.model.parser.impl.IParser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Slf4j
public class GameParser implements IParser<Game, GameDto> {
    
    public  GameDto convertToDto(Game entity) {
        ModelMapper modelMapper = new ModelMapper();
        GameDto dto = modelMapper.map(entity, GameDto.class);
        return dto;
    }

    public  List<GameDto> convertToDtos(List<Game> entities) {
        List<GameDto> dtos = new ArrayList<>();
        entities.forEach((Game entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public Game convertToEntity(GameDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Game entity = modelMapper.map(dto, Game.class);
        return entity;
    }

    public Game convertToEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Game.class);
    }

    public String convertToEntity(Game entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public List<Game> convertToEntities(List<GameDto> dtos) {
        ArrayList<Game> entities = new ArrayList<>();
        dtos.forEach((GameDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public List<Game> convertToEntities(String json) {

        List<Game> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Game>> typeReference = new TypeReference<>() {
        };

        try {
            entities = mapper.readValue(json, typeReference);
            log.info( Location.class.getName() + ", json file import success");
        } catch (IOException e) {
            log.info( Location.class.getName() + ", json file import fail: " + e.getMessage());
        }
        return entities;
    }
}
