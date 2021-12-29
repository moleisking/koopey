package com.koopey.api.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameDto extends BaseDto {
    
    private long duration;
    private long score;
}
