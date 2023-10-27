package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MessageDto extends BaseDto {
    
    private String receiverId;
    private String senderId;
    private Boolean delivered;
   
}
