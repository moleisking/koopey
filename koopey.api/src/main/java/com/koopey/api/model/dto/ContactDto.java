package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContactDto  extends BaseDto{
    
    public String content ;
    public String email ;
    public String subject ;
}
