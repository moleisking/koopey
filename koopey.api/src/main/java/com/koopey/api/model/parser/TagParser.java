package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.Tag;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class TagParser {
    
    public static TagDto toDto(Tag tagEntity) {
        ModelMapper modelMapper = new ModelMapper();
        TagDto userDto = modelMapper.map( tagEntity, TagDto.class);        
          return userDto;
    }

    public static Set<TagDto> toDtos(Set<Tag> entities) {
        Set<TagDto> dtos = new HashSet<>();
        entities.forEach((Tag entity) -> {          
                dtos.add(toDto(entity));           
        });
        return dtos;
    }

    public static Tag toEntity(TagDto tagDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Tag tagEntity = modelMapper.map(tagDto, Tag.class);
        return tagEntity;
    }

    public static Set<Tag> toEntities(Set<TagDto> tagDtos) throws ParseException  {
        Set<Tag> entities = new HashSet<>();
        tagDtos.forEach((TagDto dto) -> {
            try {
                entities.add(toEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public List<Tag> toEntities(String json) {

        List<Tag> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Tag>> typeReference = new TypeReference<>() {
        };

        try {
            entities = mapper.readValue(json, typeReference);
            log.info( Location.class.getName() + ", json file import success");
        } catch (IOException e) {
            log.info( Location.class.getName() + ", json file import fail: " + e.getMessage());
        }
        return entities;
    }

    public String toString(Tag entity) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public String toString(List<Tag> entities) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entities);
    }

}
