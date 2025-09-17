package com.koopey.api.model.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koopey.api.model.dto.base.BaseDto;
import com.koopey.api.model.entity.base.BaseEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IParser<E extends BaseEntity, D extends BaseDto> {
    D toDto(E entity);

    List<D> toDtos(List<E> entities);

    E toEntity(D dto) throws ParseException;

    E toEntity(String json) throws JsonProcessingException, ParseException;

    List<E> toEntities(List<D> dtos) throws ParseException;

    List<E> toEntities(String json) throws ParseException;

    String toString(E entity) throws JsonProcessingException,ParseException;

    String toString(List<E> entities) throws JsonProcessingException,ParseException;
}
