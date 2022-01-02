package com.koopey.api.model.dto.base;

import lombok.Data;

@Data
public class AuditDto extends BaseDto{
    protected String name;
    protected String description;
    protected String type;
}
