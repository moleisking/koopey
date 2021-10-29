package com.koopey.api.model.parser;

import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Transaction;
import java.text.ParseException;
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
        return entity;
    }
}
