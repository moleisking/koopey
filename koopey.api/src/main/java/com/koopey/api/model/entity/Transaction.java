/**
* The Transaction table is the heart of the system with transactions taking place over a period of 
* time, with multiple table links to Asset, Location and User. Hence the concept that user 
* appointments and locations are stored in the Transaction table.
*
* @author  Scott Johnston
* @version 1.0
* @since   2021-11-03 
*/
package com.koopey.api.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {

    @Column(name = "asset_id", length = 16, nullable = true, unique = false)
    protected UUID assetId;

    @Size(min = 3, max = 100)
    @Column(name = "currency", nullable = true, unique = false)
    private String currency;

    @Column(name = "buyer_id", length = 16, nullable = true, unique = false)
    protected UUID buyerId;

    @Column(name = "destination_id", length = 16, nullable = true, unique = false)
    protected UUID destinationId;

    @Column(name = "seller_id", length = 16, nullable = false, unique = false)
    protected UUID sellerId;

    @Column(name = "source_id", length = 16, nullable = false, unique = false)
    protected UUID sourceId;

    @Column(name = "quantity", nullable = false, unique = false)
    private Integer quantity;

    @Column(name = "total", nullable = false, unique = false)
    private BigDecimal total;

    @Column(name = "value", nullable = false, unique = false)
    private BigDecimal value;

    @Column(name = "reference", nullable = true, unique = false)
    private String reference;

    @Column(name = "start", nullable = true, unique = false)
    public Date start;

    @Column(name = "end", nullable = true, unique = false)
    public Date end;
}
