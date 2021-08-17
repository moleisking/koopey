package com.koopey.api.model.parser;

import com.koopey.api.model.dto.ImageDto;
import com.koopey.api.model.entity.Image;
import java.text.ParseException;
import org.modelmapper.ModelMapper;

public class ImageParser {
    
    public ImageDto convertToDto(Image imageEntity) {
        ModelMapper modelMapper = new ModelMapper();
        ImageDto userDto = modelMapper.map( imageEntity, ImageDto.class);        
          return userDto;
    }

    public Image convertToEntity(ImageDto imageDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Image imageEntity = modelMapper.map(imageDto, Image.class);
        return imageEntity;
    }
}
