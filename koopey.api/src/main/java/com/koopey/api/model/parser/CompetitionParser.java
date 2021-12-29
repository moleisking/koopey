package com.koopey.api.model.parser;

import com.koopey.api.model.dto.CompetitionDto;
import com.koopey.api.model.entity.Competition;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class CompetitionParser {

    public static CompetitionDto convertToDto(Competition entity) {
        return convertToDto(entity, false);
    }

    public static CompetitionDto convertToDto(Competition entity, Boolean children) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CompetitionDto dto = modelMapper.map(entity, CompetitionDto.class);
             
        if (children && entity.getGameId() != null) {
           // dto.game = GameParser.convertToDto(entity.getGame());
        }
        if (children && entity.getUserId() != null) {
          //  dto.player = UserParser.convertToDto(entity.getPlayer());
        }
        return dto;
    }

    public static List<CompetitionDto> convertToDtos(List<Competition> entities) {
        return convertToDtos(entities, false);
    }

    public static List<CompetitionDto> convertToDtos(List<Competition> entities, Boolean children) {
        List<CompetitionDto> dtos = new ArrayList<>();
        entities.forEach((Competition entity) -> {
            if (children){
                dtos.add(convertToDto(entity, true));
            } else {
                dtos.add(convertToDto(entity));
            }            
        });
        return dtos;
    }

    public static Competition convertToEntity(CompetitionDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Competition entity = modelMapper.map(dto, Competition.class);
        if (dto.getGameId() != null) {
            entity.setGameId(UUID.fromString(dto.getGameId()));
        }
        if (dto.getUserId() != null) {
            entity.setUserId(UUID.fromString(dto.getUserId()));
        }
        return entity;
    }
}
