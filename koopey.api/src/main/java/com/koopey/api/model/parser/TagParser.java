package com.koopey.api.model.parser;

import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.entity.Tag;
import java.text.ParseException;
import org.modelmapper.ModelMapper;

public class TagParser {
    
    public static TagDto convertToDto(Tag tagEntity) {
        ModelMapper modelMapper = new ModelMapper();
        TagDto userDto = modelMapper.map( tagEntity, TagDto.class);        
          return userDto;
    }

    public static Tag convertToEntity(TagDto tagDto) throws ParseException  {
        ModelMapper modelMapper = new ModelMapper();
        Tag tagEntity = modelMapper.map(tagDto, Tag.class);
        return tagEntity;
    }
}
