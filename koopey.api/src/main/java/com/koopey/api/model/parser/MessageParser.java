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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Slf4j
public class MessageParser implements IParser<Message, MessageDto> {

    public MessageDto convertToDto(Message entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, MessageDto.class);
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
        return modelMapper.map(dto, Message.class);
    }

    public Message convertToEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Message.class);
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
