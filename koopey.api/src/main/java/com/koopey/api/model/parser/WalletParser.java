package com.koopey.api.model.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
    
      public WalletDto convertToDto(Wallet entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, WalletDto.class);
    }

    public List<WalletDto> convertToDtos(List<Wallet> entities) {
        List<WalletDto> dtos = new ArrayList<>();
        entities.forEach((Wallet entity) -> {
            dtos.add(convertToDto(entity));
        });
        return dtos;
    }

    public Wallet convertToEntity(WalletDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Wallet.class);
    }

    public Wallet convertToEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Wallet.class);
    }

    public List<Wallet> convertToEntities(List<WalletDto> dtos) {
        ArrayList<Wallet> entities = new ArrayList<>();
        dtos.forEach((WalletDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public String convertToJson(Wallet entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }
}
