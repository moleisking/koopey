package com.koopey.api.model.parser;

import com.koopey.api.model.dto.ImageDto;
import com.koopey.api.model.entity.Image;
import java.text.ParseException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class ImageParser {
    
    public static ImageDto convertToDto(Image imageEntity) {
        ModelMapper modelMapper = new ModelMapper();
        ImageDto userDto = modelMapper.map( imageEntity, ImageDto.class);        
          return userDto;
    }

    public static ArrayList<ImageDto> convertToDtos(ArrayList<Image> entities) {
        ArrayList<ImageDto> dtos = new ArrayList<>();
        entities.forEach((Image entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public static Image convertToEntity(ImageDto imageDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Image imageEntity = modelMapper.map(imageDto, Image.class);
        return imageEntity;
    }

    public static ArrayList<Image> convertToEntities(ArrayList<ImageDto> dtos) {
        ArrayList<Image> entities = new ArrayList<>();
        dtos.forEach((ImageDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {                
                log.error(ex.getMessage());
            }
        });
        return entities;
    }
}
