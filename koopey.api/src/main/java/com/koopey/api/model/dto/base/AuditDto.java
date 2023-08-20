package com.koopey.api.model.dto.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuditDto extends BaseDto{
    protected String name;
    protected String description;
    protected String type;
}
