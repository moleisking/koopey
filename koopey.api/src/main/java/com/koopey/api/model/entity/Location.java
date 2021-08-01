package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column(name = "latitude")
    private long latitude;

    @Column(name = "longitude")
    private long longitude;

    @Column(name = "address")
    private String address;

    @JoinColumn(name = "owner_id", nullable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    private User owner;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("locations")  
    @JoinTable(name = "journey", joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<Asset> assets = new HashSet<>();

    // @Override
    // public String toString() {
    //     return MoreObjects.toStringHelper(this).add("id", id).add("latitude", latitude).add("longitude", longitude)
    //             .add("type", type).add("address", address).add("type", type).add("publish", publishDate).toString();
    // }
}