package com.koopey.api.model.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.koopey.api.model.entity.Location;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.WalletDto;
import com.koopey.api.model.entity.Wallet;
import com.koopey.api.model.parser.impl.IParser;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class WalletParser implements IParser<Wallet, WalletDto> {
    
      public WalletDto toDto(Wallet entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, WalletDto.class);
    }

    public List<WalletDto> toDtos(List<Wallet> entities) {
        List<WalletDto> dtos = new ArrayList<>();
        entities.forEach((Wallet entity) -> {
            dtos.add(toDto(entity));
        });
        return dtos;
    }

    public Wallet toEntity(WalletDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Wallet.class);
    }

    public Wallet toEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Wallet.class);
    }

    public String toEntity(Wallet entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public List<Wallet> toEntities(List<WalletDto> dtos) {
        ArrayList<Wallet> entities = new ArrayList<>();
        dtos.forEach((WalletDto dto) -> {
            try {
                entities.add(toEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public List<Wallet> toEntities(String json) {

        List<Wallet> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Wallet>> typeReference = new TypeReference<>() {
        };

        try {
            entities = mapper.readValue(json, typeReference);
            log.info( Wallet.class.getName() + ", json file import success");
        } catch (IOException e) {
            log.info( Wallet.class.getName() + ", json file import fail: " + e.getMessage());
        }
        return entities;
    }

    public String toString(Wallet entity) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public String toString(List<Wallet> entities) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entities);
    }

}
