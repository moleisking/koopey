package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
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
@Table(name = "location")
public class Location extends BaseEntity {

    private static final long serialVersionUID = 7523090550210573431L;

    @Column(name = "altitude")
    private BigDecimal altitude;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "distance")
    private BigDecimal distance;

    @Column(name = "place")
    private String place;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("assets")
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @ManyToMany()
    private List<Asset> purchases = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("assets")
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @ManyToMany()
    private List<Asset> sales = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("collections")
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @ManyToMany()
    private List<User> buyers = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("deliveries")
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false))
    @ManyToMany()
    private List<User> sellers = new ArrayList<>();

}