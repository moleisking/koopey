package com.koopey.api.model.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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

    public AssetDto toDto(Asset entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, AssetDto.class);
    }

    public List<AssetDto> toDtos(List<Asset> entities) {
        List<AssetDto> dtos = new ArrayList<>();
        entities.forEach((Asset entity) -> {
            dtos.add(toDto(entity));
        });
        return dtos;
    }

    public Asset toEntity(AssetDto dto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Asset.class);
    }

    public Asset toEntity(String json) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Asset.class);
    }

    public String toEntity(Asset entity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public List<Asset> toEntities(List<AssetDto> dtos) throws ParseException {
        ArrayList<Asset> entities = new ArrayList<>();
        dtos.forEach((AssetDto dto) -> {
            try {
                entities.add(toEntity(dto));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        });
        return entities;
    }

    public List<Asset> toEntities(String json) {

        List<Asset> entities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<Asset>> typeReference = new TypeReference<>() {
        };

        try {
            entities = mapper.readValue(json, typeReference);
            log.info( Asset.class.getName() + ", json file import success");
        } catch (IOException e) {
            log.info( Asset.class.getName() + ", json file import fail: " + e.getMessage());
        }
        return entities;
    }

    public String toString(Asset entity) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public String toString(List<Asset> entities) throws JsonProcessingException,ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entities);
    }
}
