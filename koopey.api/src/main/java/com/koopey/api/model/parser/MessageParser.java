package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.Message;
import com.koopey.api.model.parser.impl.IParser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class MessageParser implements IParser<Message, MessageDto> {

    public MessageDto convertToDto(Message entity) {
        ModelMapper modelMapper = new ModelMapper();
        MessageDto dto = modelMapper.map(entity, MessageDto.class);
        return dto;
    }

    public List<MessageDto> convertToDtos(List<Message> entities) {
        List<MessageDto> dtos = new ArrayList<>();
        entities.forEach((Message entity) -> {
            dtos.add(convertToDto(entity));
        });
        return dtos;
    }

    public Message convertToEntity(MessageDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Message entity = modelMapper.map(dto, Message.class);
        return entity;
    }

    public Message convertToEntity(String json) throws JsonProcessingException, ParseException {

        Message entity = new Message();
        ObjectMapper mapper = new ObjectMapper();
        entity = mapper.readValue(json, Message.class);
        return entity;

    }

    public String convertToJson(Message entity) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public List<Message> convertToEntities(List<MessageDto> dtos) throws ParseException {
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
