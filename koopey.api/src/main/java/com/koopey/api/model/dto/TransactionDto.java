package com.koopey.api.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class TransactionDto implements Serializable {

    public UUID id = UUID.randomUUID();
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
