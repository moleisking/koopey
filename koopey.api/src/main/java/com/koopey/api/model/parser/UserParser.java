package com.koopey.api.model.parser;

import com.koopey.api.model.dto.UserRegisterDto;
import com.koopey.api.model.entity.User;
import java.text.ParseException;
import org.modelmapper.ModelMapper;

public class UserParser {

    public UserRegisterDto convertToDto(User userEntity) {
        ModelMapper modelMapper = new ModelMapper();
        UserRegisterDto userDto = modelMapper.map( userEntity, UserRegisterDto.class);        
          return userDto;
    }

    public User convertToEntity(UserRegisterDto userDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        User userEntity = modelMapper.map(userDto, User.class);
        return userEntity;
    }
   
}
