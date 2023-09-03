package com.koopey.api.model.parser.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koopey.api.model.dto.base.BaseDto;
import com.koopey.api.model.entity.base.BaseEntity;

public interface IParser<E extends BaseEntity, D extends BaseDto >  {
    public D convertToDto(E entity);
    public List<D> convertToDtos(List<E> entities);
    public E convertToEntity(D dto) throws ParseException;
    public  E convertToEntity(String json) throws JsonProcessingException, ParseException;
    public String convertToJson(E entity) throws IOException;
    public List<E> convertToEntities(List<D> dtos) throws ParseException ;
}
