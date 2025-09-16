package com.koopey.api.model.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koopey.api.model.dto.base.BaseDto;
import com.koopey.api.model.entity.base.BaseEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IParser<E extends BaseEntity, D extends BaseDto> {
    D convertToDto(E entity);

    List<D> convertToDtos(List<E> entities);

    E convertToEntity(D dto) throws ParseException;

    E convertToEntity(String json) throws JsonProcessingException, ParseException;

    String convertToEntity(E entity) throws IOException;

    List<E> convertToEntities(List<D> dtos) throws ParseException;

    List<E> convertToEntities(String json) throws ParseException;
}
