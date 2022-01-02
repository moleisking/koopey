package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends AuditDto {
    
    public String receiverId;
    public String senderId;
    public Boolean delivered;
   
}
