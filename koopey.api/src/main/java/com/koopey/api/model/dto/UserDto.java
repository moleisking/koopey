package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AuditDto {
    
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
