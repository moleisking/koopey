package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "location")
public class Location extends BaseEntity {

    private static final long serialVersionUID = 7523090550210573431L;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "latitude")
    private long latitude;

    @Column(name = "longitude")
    private long longitude;
  
    @Column(name = "place")
    private String place;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("locations")  
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<Asset> assets = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("collections")  
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "destination_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<User> buyers = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("deliveries")  
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<User> sellers = new HashSet<>();
    
}