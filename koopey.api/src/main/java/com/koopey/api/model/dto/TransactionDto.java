package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends BaseDto {

    private AdvertDto advert;
    private AssetDto asset;
    private LocationDto destination;
    private LocationDto source;
    private UserDto buyer;
    private UserDto seller;
    private String advertId;
    private String assetId;
    private String buyerId;
    private String destinationId;
    private String sellerId;
    private String sourceId;
    private String currency;
    private String reference;
    private BigDecimal total;
    private BigDecimal value;
    private Integer grade;
    private Integer quantity;
}
