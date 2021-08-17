package com.koopey.api.model.parser;

import com.koopey.api.model.dto.LocationDto;
import com.koopey.api.model.entity.Location;
import java.text.ParseException;
import org.modelmapper.ModelMapper;

public class LocationParser {
    
    public LocationDto convertToDto(Location locationEntity) {
        ModelMapper modelMapper = new ModelMapper();
        LocationDto userDto = modelMapper.map( locationEntity, LocationDto.class);        
          return userDto;
    }

    public Location convertToEntity(LocationDto locationDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Location locationEntity = modelMapper.map(locationDto, Location.class);
        return locationEntity;
    }

}
