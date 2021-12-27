package com.koopey.api.model.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends BaseDto {
        
    public BigDecimal altitude ;
    public BigDecimal distance ;
    public BigDecimal latitude ;
    public BigDecimal longitude ; 
}