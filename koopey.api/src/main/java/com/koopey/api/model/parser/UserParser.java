package com.koopey.api.model.parser;

import com.koopey.api.model.dto.UserDto;
import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

public class UserParser {

    public static UserDto toDto(User entity) {
        ModelMapper modelMapper = new ModelMapper();      
        UserDto dto = modelMapper.map( entity, UserDto.class);        
          return dto;
    }

    public static List<UserDto> toDtos(List<User> entities) {
        List<UserDto> dtos = new ArrayList<>();
        entities.forEach((User entity) -> {          
                dtos.add(toDto(entity));
        });
        return dtos;
    }

    public static User toEntity(UserRegisterDto dto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        User entity = modelMapper.map(dto, User.class);
        return entity;
    }  
  
}
