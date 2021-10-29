package com.koopey.api.model.parser;

import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.entity.Asset;
import java.text.ParseException;
import org.modelmapper.ModelMapper;

public class AssetParser {

     public static AssetDto convertToDto(Asset assetEntity) {
        ModelMapper modelMapper = new ModelMapper();
        AssetDto userDto = modelMapper.map( assetEntity, AssetDto.class);        
          return userDto;
    }

    public static Asset convertToEntity(AssetDto assetDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Asset assetEntity = modelMapper.map(assetDto, Asset.class);
        return assetEntity;
    }
    
}
