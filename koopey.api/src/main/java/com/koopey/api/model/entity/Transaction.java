package com.koopey.api.model.entity;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {

    @Column(name = "asset_id", length = 16)
    protected UUID assetId;

    @Column(name = "customer_id", length = 16)
    protected UUID customerId; 

    @Column(name = "destination_id", length = 16)
    protected UUID destinationId;

    @Column(name = "provider_id", length = 16)
    protected UUID providerId;

    @Column(name = "source_id", length = 16, nullable = false)
    protected UUID sourceId;

    @Column(name = "value",  nullable = false)
    private BigDecimal value;
  
    @Column(name = "reference")
    private String reference;
}
