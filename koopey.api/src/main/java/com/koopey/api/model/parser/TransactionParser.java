package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.model.parser.impl.IParser;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@NoArgsConstructor
@Slf4j
public class TransactionParser implements IParser<Transaction, TransactionDto> {

    AssetParser assetParser;
    LocationParser locationParser;
    TransactionParser TransactionParser;

    public TransactionDto toDto(Transaction entity) {
        return toDto(entity, false);
    }

    public TransactionDto toDto(Transaction entity, Boolean children) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TransactionDto dto = modelMapper.map(entity, TransactionDto.class);
        if (children && entity.getAssetId() != null) {
            dto.setAsset(assetParser.toDto(entity.getAsset()));
            Set<TagDto> tagDtos = TagParser.toDtos(entity.getAsset().getTags());
            dto.getAsset().setTags(tagDtos);
        }
        if (children && entity.getSourceId() != null) {
            dto.setSource(locationParser.toDto(entity.getSource()));
        }
        if (children && entity.getBuyerId() != null) {
            dto.setBuyer(UserParser.toDto(entity.getBuyer()));
        }
        if (children && entity.getSellerId() != null) {
            dto.setSeller(UserParser.toDto(entity.getSeller()));
        }
        return dto;
    }

    public List<TransactionDto> toDtos(List<Transaction> entities) {
        return toDtos(entities, false);
    }

    public List<TransactionDto> toDtos(List<Transaction> entities, Boolean children) {
        List<TransactionDto> dtos = new ArrayList<>();
        entities.forEach((Transaction entity) -> {
            if (children) {
                dtos.add(toDto(entity, true));
            } else {
                dtos.add(toDto(entity));
            }
        });
        return dtos;
    }

    public Transaction toEntity(TransactionDto dto) throws ParseException {
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

    public Transaction toEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Transaction.class);
    }

    public List<Transaction> toEntities(List<TransactionDto> dtos) {
        ArrayList<Transaction> entities = new ArrayList<>();
        dtos.forEach((TransactionDto dto) -> {
            try {
                entities.add(toEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public List<Transaction> toEntities(String json) {

        List<Transaction> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Transaction>> typeReference = new TypeReference<>() {
        };

        try {
            entities = mapper.readValue(json, typeReference);
            log.info( Transaction.class.getName() + ", json file import success");
        } catch (IOException e) {
            log.info( Transaction.class.getName() + ", json file import fail: " + e.getMessage());
        }
        return entities;
    }

    public String toString(Transaction entity) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public String toString(List<Transaction> entities) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entities);
    }
}
