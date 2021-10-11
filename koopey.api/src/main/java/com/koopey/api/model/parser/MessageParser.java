package com.koopey.api.model.parser;

import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.Message;
import java.text.ParseException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class MessageParser {
    
    public MessageDto convertToDto(Message entity) {
        ModelMapper modelMapper = new ModelMapper();
        MessageDto dto = modelMapper.map(entity, MessageDto.class);
        return dto;
    }

    public ArrayList<MessageDto> convertToDtos(ArrayList<Message> entities) {
        ArrayList<MessageDto> dtos = new ArrayList<>();
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

    public ArrayList<Message> convertToEntities(ArrayList<MessageDto> dtos) {
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
