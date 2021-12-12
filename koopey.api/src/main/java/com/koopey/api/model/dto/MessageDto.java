package com.koopey.api.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends BaseDto {
    
    public String receiverId;
    public String senderId;
    public Boolean delivered;
   
}
