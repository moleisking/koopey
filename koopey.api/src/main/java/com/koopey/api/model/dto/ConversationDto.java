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
public class ConversationDto extends BaseDto {

    public String messageId;
    public String userId;
    public Boolean received;
    public Boolean sent;
}
