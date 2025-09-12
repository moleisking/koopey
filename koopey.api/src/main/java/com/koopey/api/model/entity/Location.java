package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper = true )
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@SuperBuilder
@Table(name = "location")
public class Location extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 7523090550210573431L;

    @Column(name = "altitude")
    @Digits(integer=10, fraction=9)
    private BigDecimal altitude;

    @Column(name = "latitude")
    @Digits(integer=3, fraction=9)
    private BigDecimal latitude;

    @Column(name = "longitude")
    @Digits(integer=3, fraction=9)
    private BigDecimal longitude;

    @Column(name = "distance")
    private BigDecimal distance;  

    @Column(name = "place")
    private String place;  

    @Column(name = "owner_id", length = 16, nullable = true, unique = false, columnDefinition = "VARCHAR(36)")
    protected UUID ownerId;
    
    @Builder.Default
    @EqualsAndHashCode.Exclude    
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore  
    @ManyToMany()
    private List<Asset> purchases = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude   
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore   
    @ManyToMany()
    private List<Asset> sales = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude   
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore   
    @ManyToMany()
    @ToString.Exclude
    private List<User> buyers = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude     
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false))
    @JsonIgnore   
    @ManyToMany()
    @ToString.Exclude
    private List<User> sellers = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @OneToMany(mappedBy="source",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> sourceTransactions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @OneToMany(mappedBy="destination",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> destinationTransactions = new ArrayList<>();

}