package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.LocationDto;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.parser.impl.IParser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Slf4j
public class LocationParser implements IParser<Location, LocationDto> {

    public LocationDto convertToDto(Location entity) {
        ModelMapper modelMapper = new ModelMapper();
        LocationDto dto = modelMapper.map(entity, LocationDto.class);
        if (entity.getOwnerId() != null) {
            dto.setOwnerId(entity.getOwnerId().toString());
        }
        return dto;
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
        Location location = modelMapper.map(dto, Location.class);
        if (dto.getOwnerId() != null && !dto.getOwnerId().isEmpty()) {
            location.setOwnerId(UUID.fromString(dto.getOwnerId()));
        }
        return location;
    }

    public Location convertToEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Location.class);
    }

    public String convertToEntity(Location entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
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

    public List<Location> convertToEntities(String json) {

        List<Location> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Location>> typeReference = new TypeReference<>() {
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
