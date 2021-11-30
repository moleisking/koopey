package com.koopey.api.model.dto;

import lombok.Data;

@Data
public class UserDto extends BaseDto {
    
    private String alias;
    private String avatar;
    private String currency;
    private String language;
    private Integer average;
    private Integer positive;
    private Integer negative;
    private Double latitude;
    private Double longitude;
    private Boolean notify;
    private Boolean gdpr;
    
}
