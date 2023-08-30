package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends AuditDto {
        
    public BigDecimal altitude ;
    public BigDecimal distance ;
    public BigDecimal latitude ;
    public BigDecimal longitude ; 
}