package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "asset")
public class Asset extends BaseEntity {

    private static final long serialVersionUID = 7523090550210693431L;
  
    @Column(name = "currency")
    private String currency;

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
    private Date manufactureDate;

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

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("purchases")
    @ManyToMany(mappedBy = "purchases" )
    private Set<Location> destinations = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("sales")
    @ManyToMany(mappedBy = "sales" )
    private Set<Location> sources = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("assets")
    @ManyToMany(mappedBy = "assets" )
    private Set<Tag> tags = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("purchases")
    @ManyToMany(mappedBy = "purchases" )
    private Set<User> buyers = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("sales")
    @ManyToMany(mappedBy = "sales" )
    private Set<User> sellers = new HashSet<>();
   
}