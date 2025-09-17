package com.koopey.api.model.dto;

import java.util.UUID;

import com.koopey.api.model.dto.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClassificationDto  extends BaseDto {

    public String assetId ;
    public String tagId ;
}
