package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.entity.base.AuditEntity;
import com.koopey.api.model.entity.base.BaseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class MessageParser {

    public static MessageDto convertToDto(Message entity) {
        ModelMapper modelMapper = new ModelMapper();
        MessageDto dto = modelMapper.map(entity, MessageDto.class);
        return dto;
    }

    public static List<MessageDto> convertToDtos(List<Message> entities) {
        List<MessageDto> dtos = new ArrayList<>();
        entities.forEach((Message entity) -> {
            dtos.add(convertToDto(entity));
        });
        return dtos;
    }

    public static Message convertToEntity(MessageDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Message entity = modelMapper.map(dto, Message.class);
        return entity;
    }

    public static Message convertToEntity(String json) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Message entity = modelMapper.map(json, Message.class);
        return entity;
        /*
         * Message result = new Message();
         * try {
         * ObjectMapper mapper = new ObjectMapper();
         * JavaType type =
         * mapper.getTypeFactory().constructParametricType(Message.class,
         * AuditEntity.class, BaseEntity.class);
         * result= mapper.readValue(json, type);
         * } catch (JsonProcessingException ex) {
         * log.info("RabbitMQ send senderID : {}", ex.getMessage());
         * }
         * return result;
         */
    }

    public static String convertToJson(Message entity) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        String str = modelMapper.map(entity, String.class);
        return str;
        /*
         * Message result = new Message();
         * try {
         * ObjectMapper mapper = new ObjectMapper();
         * JavaType type =
         * mapper.getTypeFactory().constructParametricType(Message.class,
         * AuditEntity.class, BaseEntity.class);
         * result= mapper.readValue(json, type);
         * } catch (JsonProcessingException ex) {
         * log.info("RabbitMQ send senderID : {}", ex.getMessage());
         * }
         * return result;
         */
    }

    public static List<Message> convertToEntities(List<MessageDto> dtos) {
        ArrayList<Message> entities = new ArrayList<>();
        dtos.forEach((MessageDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }
}
