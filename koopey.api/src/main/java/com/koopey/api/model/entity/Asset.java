package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.AuditEntity;
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
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "asset")
public class Asset extends AuditEntity {

    private static final long serialVersionUID = 7523090550210693431L;

    @Column(name = "currency")
    private String currency;

    @Column(name = "data")
    @ToString.Exclude
    private String data;

    @Column(name = "dimension_unit")
    private String dimensionUnit;

    @Column(name = "first_image")
    @Size(max = 1048576)
    @ToString.Exclude
    private String firstImage;

    @Column(name = "secondImage")
    @Size(max = 1048576)
    @ToString.Exclude
    private String secondImage;

    @Column(name = "third_image")
    @Size(max = 1048576)
    @ToString.Exclude
    private String thirdImage;

    @Column(name = "fourth_image")
    @Size(max = 1048576)
    @ToString.Exclude
    private String fourthImage;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "serial")
    private String serial;

    @Column(name = "weight_unit")
    private String weightUnit;

    @Builder.Default
    @Column(name = "average")
    private Integer average = 0;

    @Builder.Default
    @Column(name = "height")
    private Integer height = 0;

    @Builder.Default
    @Column(name = "length")
    private Integer length = 0;

    @Builder.Default
    @Column(name = "positive")
    private Integer positive = 0;

    @Builder.Default
    @Column(name = "negative")
    private Integer negative = 0;

    @Builder.Default
    @Column(name = "quantity")
    private Integer quantity = 1;

    @Column(name = "value")
    private Integer value;

    @Builder.Default
    @Column(name = "weight")
    private Integer weight = 0;

    @Builder.Default
    @Column(name = "width")
    private Integer width = 0;

    @Column(name = "manufacture_date")
    private Date manufactureDate;

    @Builder.Default
    @Column(name = "available")
    private boolean available = true;

    @JsonIgnore
    @OneToOne
    private Advert advert;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "purchases")
    @ToString.Exclude
    private List<Location> destinations = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "sales")
    @ToString.Exclude
    private List<Location> sources = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "assets")
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "purchases")
    @ToString.Exclude
    private List<User> buyers = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "sales")
    @ToString.Exclude
    private List<User> sellers = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asset")
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Classification.class, mappedBy = "asset")
    @ToString.Exclude
    private List<Classification> classifications = new ArrayList<>();

}