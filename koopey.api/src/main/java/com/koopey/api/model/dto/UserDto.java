package com.koopey.api.model.dto;

import java.math.BigDecimal;

import com.koopey.api.model.dto.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserDto extends BaseDto {

    private String alias;
    private String avatar;
    private String currency;
    private String language;
    private Integer average;
    private Integer positive;
    private Integer negative;
    private BigDecimal altitude;
    private BigDecimal latitude;
    private BigDecimal longitude;   
     private Boolean cookie;
    private Boolean gdpr;
    private Boolean track;
    private Boolean notify;

}
