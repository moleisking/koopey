package com.koopey.api.model.parser;

import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Transaction;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionParser {

    public static TransactionDto convertToDto(Transaction entity) {
        ModelMapper modelMapper = new ModelMapper();
        TransactionDto dto = modelMapper.map(entity, TransactionDto.class);
        return dto;
    }

    public static List<TransactionDto> convertToDtos(List<Transaction> entities) {
        List<TransactionDto> dtos = new ArrayList<>();
        entities.forEach((Transaction entity) -> {
            dtos.add(convertToDto(entity));
        });
        return dtos;
    }

    public static TransactionDto convertToDtoWithChildren(Transaction entity) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TransactionDto dto = modelMapper.map(entity, TransactionDto.class);
        dto.asset = AssetParser.convertToDto(entity.getAsset());
        //dto.destination = LocationParser.convertToDto(entity.getDestination());
        try {
            log.info("source {}", entity.getSource().toString());
            dto.source = LocationParser.convertToDto(entity.getSource());
        } catch (Exception e) {
            log.info("source error {}", e.getMessage());
        }
        dto.buyer = UserParser.convertToDto(entity.getBuyer());
        dto.seller = UserParser.convertToDto(entity.getSeller());
        return dto;
    }

    public static List<TransactionDto> convertToDtosWithChildren(List<Transaction> entities) {
        List<TransactionDto> dtos = new ArrayList<>();
        entities.forEach((Transaction entity) -> {
            dtos.add(convertToDtoWithChildren(entity));
        });
        return dtos;
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
