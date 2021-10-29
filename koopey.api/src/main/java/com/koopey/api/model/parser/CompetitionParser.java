package com.koopey.api.model.parser;

import com.koopey.api.model.dto.CompetitionDto;
import com.koopey.api.model.entity.Competition;
import java.text.ParseException;
import java.util.UUID;
import org.modelmapper.ModelMapper;

public class CompetitionParser {

    public static CompetitionDto convertToDto(Competition entity) {
        ModelMapper modelMapper = new ModelMapper();
        CompetitionDto dto = modelMapper.map(entity, CompetitionDto.class);
        return dto;
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
