package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AdvertDto extends AuditDto {
    
    private Date end;
    private Date start;
}
