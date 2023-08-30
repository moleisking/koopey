package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends AuditDto {

    public AdvertDto advert;
    public AssetDto asset;
    public LocationDto destination;
    public LocationDto source;
    public UserDto buyer;
    public UserDto seller;
    public String advertId;
    public String assetId;
    public String buyerId;
    public String destinationId;
    public String sellerId;
    public String sourceId;
    public String currency;
    public String reference;
    public BigDecimal total;
    public BigDecimal value;
    public Integer grade;
    public Integer quantity;
}
