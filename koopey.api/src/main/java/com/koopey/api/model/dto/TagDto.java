package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TagDto extends BaseDto{
    
    private String cn;    
    private String de;
    private String en;
    private String es;
    private String fr;
    private String it;
    private String nl;
    private String pt;
}
