package com.koopey.api.model.parser;

import com.koopey.api.model.dto.UserDto;
import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import java.text.ParseException;
import org.modelmapper.ModelMapper;

public class UserParser {

    public static UserDto convertToDto(User userEntity) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map( userEntity, UserDto.class);        
          return userDto;
    }

    public static User convertToEntity(UserRegisterDto userDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        User userEntity = modelMapper.map(userDto, User.class);
        return userEntity;
    }
   
}
