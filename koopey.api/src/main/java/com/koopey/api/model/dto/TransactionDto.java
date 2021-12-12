package com.koopey.api.model.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends BaseDto {

    public String assetId;
    public String sourceId;
    public String destinationId;
    public String sellerId;
    public String buyerId;
    public String currency;
    public String reference;
    public Integer quantity;
    public BigDecimal total;
    public BigDecimal value;
}
