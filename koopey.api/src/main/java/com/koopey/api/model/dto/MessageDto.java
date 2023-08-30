package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends AuditDto {
    
    public String receiverId;
    public String senderId;
    public Boolean delivered;
   
}
