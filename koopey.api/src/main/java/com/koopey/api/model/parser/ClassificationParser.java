package com.koopey.api.model.parser;

import com.koopey.api.model.dto.ClassificationDto;
import com.koopey.api.model.entity.Classification;
import java.text.ParseException;
import java.util.UUID;
import org.modelmapper.ModelMapper;

public class ClassificationParser {

    public static ClassificationDto convertToDto(Classification entity) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificationDto dto = modelMapper.map(entity, ClassificationDto.class);
        return dto;
    }

    public static Classification convertToEntity(ClassificationDto dto) throws ParseException {
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
}
