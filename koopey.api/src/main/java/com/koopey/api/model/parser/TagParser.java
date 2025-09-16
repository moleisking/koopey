package com.koopey.api.model.parser;

import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.entity.Tag;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
@Slf4j
public class TagParser {
    
    public static TagDto convertToDto(Tag tagEntity) {
        ModelMapper modelMapper = new ModelMapper();
        TagDto userDto = modelMapper.map( tagEntity, TagDto.class);        
          return userDto;
    }

    public static Set<TagDto> convertToDtos(Set<Tag> entities) {
        Set<TagDto> dtos = new HashSet<>();
        entities.forEach((Tag entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public static Tag convertToEntity(TagDto tagDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Tag tagEntity = modelMapper.map(tagDto, Tag.class);
        return tagEntity;
    }

    public static Set<Tag> convertToEntities(Set<TagDto> tagDtos) throws ParseException  {
        Set<Tag> entities = new HashSet<>();
        tagDtos.forEach((TagDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

}
