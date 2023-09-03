package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.LocationDto;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.parser.impl.IParser;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Slf4j
public class LocationParser implements IParser<Location, LocationDto> {

    public LocationDto convertToDto(Location entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, LocationDto.class);
    }

    public List<LocationDto> convertToDtos(List<Location> entities) {
        List<LocationDto> dtos = new ArrayList<>();
        entities.forEach((Location entity) -> {
            dtos.add(convertToDto(entity));
        });
        return dtos;
    }

    public Location convertToEntity(LocationDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Location.class);
    }

    public Location convertToEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Location.class);
    }

    public List<Location> convertToEntities(List<LocationDto> dtos) {
        ArrayList<Location> entities = new ArrayList<>();
        dtos.forEach((LocationDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public String convertToJson(Location entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

}
