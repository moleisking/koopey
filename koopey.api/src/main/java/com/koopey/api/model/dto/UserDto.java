package com.koopey.api.model.dto;

import lombok.Data;

@Data
public class UserDto extends BaseDto {
    
    private String alias;
    private String avatar;
    private String currency;
    private String language;
    private Number latitude;
    private Number longitude;
    private Boolean notify;
    private Boolean gdpr;
    
}
