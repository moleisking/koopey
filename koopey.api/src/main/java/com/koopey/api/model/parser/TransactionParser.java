package com.koopey.api.model.parser;

import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Transaction;
import java.text.ParseException;
import java.util.UUID;
import org.modelmapper.ModelMapper;

public class TransactionParser {

    public static TransactionDto convertToDto(Transaction entity) {
        ModelMapper modelMapper = new ModelMapper();
        TransactionDto dto = modelMapper.map(entity, TransactionDto.class);
        return dto;
    }

    public static Transaction convertToEntity(TransactionDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Transaction entity = modelMapper.map(dto, Transaction.class);
        if (dto.getAssetId() != null) {
            entity.setAssetId(UUID.fromString(dto.getAssetId()));
        }
        if (dto.getBuyerId() != null) {
            entity.setBuyerId(UUID.fromString(dto.getBuyerId()));
        }
        if (dto.getDestinationId() != null) {
            entity.setDestinationId(UUID.fromString(dto.getDestinationId()));
        }
        if (dto.getSellerId() != null) {
            entity.setSellerId(UUID.fromString(dto.getSellerId()));
        }
        if (dto.getSourceId() != null) {
            entity.setSourceId(UUID.fromString(dto.getSourceId()));
        }
        return entity;
    }
}
