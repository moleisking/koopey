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

    @Column(name = "currency")
    private String currency;

    @Column(name = "buyer_id", length = 16)
    protected UUID buyerId; 

    @Column(name = "destination_id", length = 16)
    protected UUID destinationId;

    @Column(name = "seller_id", length = 16)
    protected UUID sellerId;

    @Column(name = "source_id", length = 16, nullable = false)
    protected UUID sourceId;

    @Column(name = "quantity",  nullable = false)
    private Integer quantity;

    @Column(name = "total",  nullable = false)
    private BigDecimal total;

    @Column(name = "value",  nullable = false)
    private BigDecimal value;
  
    @Column(name = "reference")
    private String reference;
}
