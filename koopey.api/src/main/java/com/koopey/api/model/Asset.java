package com.koopey.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Entity
@Data
@EqualsAndHashCode(exclude = "tags")
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = 7523090550210693431L;

    @Id  
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "currency")
    private String currency;

    @Column(name = "type")
    private String type;

    @Column(name = "dimension_unit")
    private String dimensionUnit;

    @Column(name = "distance")
    private int distance;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "length")
    private int length;

    @Column(name = "value")
    private int value;

    @Column(name = "weight")
    private int weight;

    @Column(name = "weight_unit")
    private String weightUnit;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufacturer_serial")
    private String manufacturer_serial;

    @Column(name = "manufacture_date")
    private long manufactureDate;

    @Column(name = "time_zone")
    private long timeZone;

    @Builder.Default
    @Column(name = "publish_date")
    private Long publishDate = System.currentTimeMillis() / 1000;

    @Column(name = "available")
    private boolean available;

    @OneToOne
    private Advert advert;
    
    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Builder.Default
    @JsonIgnoreProperties("assets")
    @ManyToMany(mappedBy = "assets" )
    private Set<Tag> tags = new HashSet<>();

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("distance", distance).add("weight", weight)
                .add("height", height).add("length", length).add("width", width).add("value", value)
                .add("currency", currency).add("description", description).add("dimensionUnit", dimensionUnit)
                .add("weightUnit", weightUnit).add("description", description).add("type", type)
                .add("manufactureDate", manufactureDate).add("timeZone", timeZone).add("publish", publishDate)
                .add("buyer", buyer.toString()).add("seller", seller.toString()).add("tags", tags.toString())
                .toString();
    }
}