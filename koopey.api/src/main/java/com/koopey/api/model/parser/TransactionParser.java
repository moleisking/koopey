package com.koopey.api.model.parser;

import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Transaction;
import lombok.NoArgsConstructor;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@NoArgsConstructor
public class TransactionParser {

    AssetParser assetParser;
    LocationParser locationParser;

    public TransactionDto convertToDto(Transaction entity) {
        return convertToDto(entity, false);
    }

    public TransactionDto convertToDto(Transaction entity, Boolean children) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TransactionDto dto = modelMapper.map(entity, TransactionDto.class);
        if (children && entity.getAssetId() != null) {
            dto.setAsset(assetParser.convertToDto(entity.getAsset()));
            Set<TagDto> tagDtos = TagParser.convertToDtos(entity.getAsset().getTags());
            dto.getAsset().setTags(tagDtos);
        }
        if (children && entity.getSourceId() != null) {
            dto.setSource(locationParser.convertToDto(entity.getSource()));
        }
        if (children && entity.getBuyerId() != null) {
            dto.setBuyer(UserParser.convertToDto(entity.getBuyer()));
        }
        if (children && entity.getSellerId() != null) {
            dto.setSeller(UserParser.convertToDto(entity.getSeller()));
        }
        return dto;
    }

    public List<TransactionDto> convertToDtos(List<Transaction> entities) {
        return convertToDtos(entities, false);
    }

    public List<TransactionDto> convertToDtos(List<Transaction> entities, Boolean children) {
        List<TransactionDto> dtos = new ArrayList<>();
        entities.forEach((Transaction entity) -> {
            if (children) {
                dtos.add(convertToDto(entity, true));
            } else {
                dtos.add(convertToDto(entity));
            }
        });
        return dtos;
    }

    public Transaction convertToEntity(TransactionDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Transaction entity = modelMapper.map(dto, Transaction.class);
        if (dto.getAdvertId() != null) {
            entity.setAdvertId(UUID.fromString(dto.getAdvertId()));
        }
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
