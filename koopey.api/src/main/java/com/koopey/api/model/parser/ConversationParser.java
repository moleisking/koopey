package com.koopey.api.model.parser;

import com.koopey.api.model.dto.ConversationDto;
import com.koopey.api.model.entity.Conversation;
import java.text.ParseException;
import java.util.UUID;
import org.modelmapper.ModelMapper;

public class ConversationParser {

    public static ConversationDto convertToDto(Conversation entity) {
        ModelMapper modelMapper = new ModelMapper();
        ConversationDto dto = modelMapper.map(entity, ConversationDto.class);
        return dto;
    }

    public static Conversation convertToEntity(ConversationDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Conversation entity = modelMapper.map(dto, Conversation.class);
        if (dto.getMessageId() != null) {
            entity.setMessageId(UUID.fromString(dto.getMessageId()));
        }
        if (dto.getUserId() != null) {
            entity.setUserId(UUID.fromString(dto.getUserId()));
        }
        return entity;
    }
}
