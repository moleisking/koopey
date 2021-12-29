package com.koopey.api.model.parser;

import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.entity.Asset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;

public class AssetParser {
    
     public static AssetDto convertToDto(Asset entity) {
        ModelMapper modelMapper = new ModelMapper();
        AssetDto dto = modelMapper.map( entity, AssetDto.class);         
        return dto;
    }
    
    public static List<AssetDto> convertToDtos(List<Asset> entities) {
        List<AssetDto> dtos = new ArrayList<>();
        entities.forEach((Asset entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public static Asset convertToEntity(AssetDto dto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Asset entity = modelMapper.map(dto, Asset.class);
        return entity;
    }
    
}
