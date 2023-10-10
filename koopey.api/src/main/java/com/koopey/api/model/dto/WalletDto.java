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
public class WalletDto extends BaseDto {

    public String ownerId;
    public String identifier;
    public String currency;
    public int value;
    public String address;
}
