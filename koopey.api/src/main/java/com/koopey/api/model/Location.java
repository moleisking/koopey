package com.koopey.api.model;

import com.google.common.base.MoreObjects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    // @Override
    // public String toString() {
    //     return MoreObjects.toStringHelper(this).add("id", id).add("latitude", latitude).add("longitude", longitude)
    //             .add("type", type).add("address", address).add("type", type).add("publish", publishDate).toString();
    // }
}