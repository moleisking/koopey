package com.koopey.api.model.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class ConversationDto implements Serializable {

    public UUID id = UUID.randomUUID();
    public String messageId;
    public String userId;
}
