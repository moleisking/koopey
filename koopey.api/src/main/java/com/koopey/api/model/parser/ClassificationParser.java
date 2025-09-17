package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.ClassificationDto;
import com.koopey.api.model.dto.ClassificationDto;
import com.koopey.api.model.entity.Classification;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.parser.impl.IParser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Slf4j
public class ClassificationParser implements IParser<Classification, ClassificationDto> {

    public ClassificationDto toDto(Classification entity) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificationDto dto = modelMapper.map(entity, ClassificationDto.class);
        return dto;
    }

    public List<ClassificationDto> toDtos(List<Classification> entities) {
        List<ClassificationDto> dtos = new ArrayList<>();
        entities.forEach((Classification entity) -> {
            dtos.add(toDto(entity));
        });
        return dtos;
    }

    public Classification toEntity(ClassificationDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Classification entity = modelMapper.map(dto, Classification.class);
        if (dto.getAssetId() != null) {
            entity.setAssetId(UUID.fromString(dto.getAssetId()));
        }
        if (dto.getTagId() != null) {
            entity.setTagId(UUID.fromString(dto.getTagId()));
        }
        return entity;
    }

    public Classification toEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Classification.class);
    }

    public List<Classification> toEntities(List<ClassificationDto> dtos) {
        ArrayList<Classification> entities = new ArrayList<>();
        dtos.forEach((ClassificationDto dto) -> {
            try {
                entities.add(toEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public List<Classification> toEntities(String json) {

        List<Classification> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Classification>> typeReference = new TypeReference<>() {
        };

        try {
            entities = mapper.readValue(json, typeReference);
            log.info( Classification.class.getName() + ", json file import success");
        } catch (IOException e) {
            log.info( Classification.class.getName() + ", json file import fail: " + e.getMessage());
        }
        return entities;
    }

    public String toString(Classification entity) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public String toString(List<Classification> entities) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entities);
    }
}
