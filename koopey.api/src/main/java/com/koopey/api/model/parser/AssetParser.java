package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.entity.Asset;
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
public class AssetParser implements IParser<Asset, AssetDto> {

    public AssetDto convertToDto(Asset entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, AssetDto.class);
    }

    public List<AssetDto> convertToDtos(List<Asset> entities) {
        List<AssetDto> dtos = new ArrayList<>();
        entities.forEach((Asset entity) -> {
            dtos.add(convertToDto(entity));
        });
        return dtos;
    }

    public Asset convertToEntity(AssetDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Asset.class);
    }

    public Asset convertToEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Asset.class);
    }

    public String convertToJson(Asset entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public List<Asset> convertToEntities(List<AssetDto> dtos) throws ParseException {
        ArrayList<Asset> entities = new ArrayList<>();
        dtos.forEach((AssetDto dto) -> {
            try {
                entities.add(convertToEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }
}
