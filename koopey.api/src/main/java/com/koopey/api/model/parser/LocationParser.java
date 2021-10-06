package com.koopey.api.model.parser;

import com.koopey.api.model.dto.LocationDto;
import com.koopey.api.model.entity.Location;
import java.text.ParseException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class LocationParser {

    public LocationDto convertToDto(Location entity) {
        ModelMapper modelMapper = new ModelMapper();
        LocationDto dto = modelMapper.map(entity, LocationDto.class);
        return dto;
    }

    public ArrayList<LocationDto> convertToDtos(ArrayList<Location> entities) {
        ArrayList<LocationDto> dtos = new ArrayList<>();
        entities.forEach((Location entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public Location convertToEntity(LocationDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Location entity = modelMapper.map(dto, Location.class);
        return entity;
    }

    public ArrayList<Location> convertToEntities(ArrayList<LocationDto> dtos) {
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

}
