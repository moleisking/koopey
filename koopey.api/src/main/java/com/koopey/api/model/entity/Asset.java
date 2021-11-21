package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "serial")
    private String serial;

    @Column(name = "weight_unit")
    private String weightUnit;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "average")
    private Float average;   

    @Column(name = "height")
    private Integer height;

    @Column(name = "length")
    private Integer length;

    @Column(name = "positive")
    private Integer positive;

    @Column(name = "negative")
    private Integer negative;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "value")
    private Integer value;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "width")
    private Integer width;

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
    @JsonIgnore
    @ManyToMany(mappedBy = "purchases")
    private List<Location> destinations = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore    
    @ManyToMany(mappedBy = "sales" )
    private List<Location> sources = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore    
    @ManyToMany(mappedBy = "assets" )
    private Set<Tag> tags = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore    
    @ManyToMany(mappedBy = "purchases" )
    private List<User> buyers = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @ManyToMany(mappedBy = "sales" )
    private List<User> sellers = new ArrayList<>();
   
}