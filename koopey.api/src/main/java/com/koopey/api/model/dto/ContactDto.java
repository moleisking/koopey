package com.koopey.api.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactDto  extends BaseDto{
    
    public String content ;
    public String email ;
    public String subject ;
}
