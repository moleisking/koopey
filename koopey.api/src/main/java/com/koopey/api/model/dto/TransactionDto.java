package com.koopey.api.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class TransactionDto implements Serializable {

    public UUID id = UUID.randomUUID();
    public String asset_id;
    public String source_id;
    public String destination_id;
    public String provider_id;
    public String customer_id;
    public String referance;
    public BigDecimal value;
}
