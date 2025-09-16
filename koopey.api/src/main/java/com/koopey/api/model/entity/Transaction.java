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
import java.sql.Types;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@SuperBuilder
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Column(name = "advert_id", length = 36, nullable = true, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID advertId;

    @Column(name = "asset_id", length = 36, nullable = true, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID assetId;

    @Size(min = 3, max = 100)
    @Column(name = "currency", nullable = true, unique = false)
    private String currency;

    @Column(name = "buyer_id", length = 36, nullable = true, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID buyerId;

    @Column(name = "destination_id", length = 36, nullable = true, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID destinationId;

    @Column(name = "seller_id", length = 36, nullable = false, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID sellerId;

    @Column(name = "source_id", length = 36, nullable = false, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID sourceId;

    @Column(name = "secret", length = 36, nullable = true, unique = true)
    protected String secret;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, targetEntity = Location.class)
    private Location source;

    @JsonIgnore()
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = Location.class)
    private Location destination;

    @JsonIgnore()
    @JoinColumn(name = "buyer_id", nullable = true, unique = true, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User buyer;

    @JsonIgnore()
    @JoinColumn(name = "seller_id", nullable = false, unique = true, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, targetEntity = User.class)
    private User seller;

}
