/**
* The Transaction table is the heart of the system with transactions taking place over a period of 
* time, with multiple table links to Asset, Location and User. Hence the concept that user 
* appointments and locations are stored in the Transaction table as they are all related at this 
* single point.
*
* @author  Scott Johnston
* @version 1.0
* @since   2021-11-03 
*/
package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Column(name = "advert_id", length = 16, nullable = true, unique = false)
    protected UUID advertId;

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

    @Column(name = "secret", length = 16, nullable = true, unique = true)
    protected UUID secret;

    @Column(name = "quantity", nullable = false, unique = false)
    private Integer quantity;

    @Column(name = "total", nullable = false, unique = false)
    private BigDecimal total;

    @Column(name = "value", nullable = false, unique = false)
    private BigDecimal value;   

    @Size(min = 0, max = 100)
    @Column(name = "grade", nullable = true, unique = false)
    private Integer grade;

    @Builder.Default
    @Column(name = "start", nullable = true, unique = false)
    public Date start = new Date();

    @Column(name = "end", nullable = true, unique = false)
    public Date end;

    @JsonIgnore()
    @JoinColumn(name = "advert_id", nullable = true, unique = false, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = Advert.class)
    private Advert advert;

    @JsonIgnore()
    @JoinColumn(name = "asset_id", nullable = false, unique = false, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, targetEntity = Asset.class)
    private Asset asset;

    @JsonIgnore()
    @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, unique = false, insertable = false, updatable = false)
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Location source;

    @JsonIgnore()
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    private Location destination;

    @JsonIgnore()
    @JoinColumn(name = "buyer_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    private User buyer;

    @JsonIgnore()
    @JoinColumn(name = "seller_id", nullable = false, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private User seller;

}
