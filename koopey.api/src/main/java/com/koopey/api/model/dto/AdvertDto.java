package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdvertDto extends AuditDto {
    
    private Date end;
    private Date start;
}
