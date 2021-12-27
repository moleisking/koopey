package com.koopey.api.model.parser;

import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.entity.Asset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;

public class AssetParser {
    
     public static AssetDto convertToDto(Asset assetEntity) {
        ModelMapper modelMapper = new ModelMapper();
        AssetDto assetDto = modelMapper.map( assetEntity, AssetDto.class);         
        return assetDto;
    }
    
    public static List<AssetDto> convertToDtos(List<Asset> entities) {
        List<AssetDto> dtos = new ArrayList<>();
        entities.forEach((Asset entity) -> {          
                dtos.add(convertToDto(entity));           
        });
        return dtos;
    }

    public static Asset convertToEntity(AssetDto assetDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Asset assetEntity = modelMapper.map(assetDto, Asset.class);
        return assetEntity;
    }
    
}
