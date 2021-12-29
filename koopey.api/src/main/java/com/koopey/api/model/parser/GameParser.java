package com.koopey.api.model.parser;

import com.koopey.api.model.dto.GameDto;
import com.koopey.api.model.entity.Game;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;

public class GameParser {
    
    public static GameDto convertToDto(Game entity) {
        ModelMapper modelMapper = new ModelMapper();
        GameDto dto = modelMapper.map(entity, GameDto.class);
        return dto;
    }

    public static List<GameDto> convertToDtos(List<Game> entities) {
        List<GameDto> dtos = new ArrayList<>();
        entities.forEach((Game entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public static Game convertToEntity(GameDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Game entity = modelMapper.map(dto, Game.class);
        return entity;
    }
}
