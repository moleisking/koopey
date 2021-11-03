package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.koopey.api.model.type.CurrencyType;
import com.koopey.api.model.type.LanguageType;
import com.koopey.api.model.type.MeasurementType;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -5133446600881698403L;

    @Size( max = 131072)
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday")
    private Date birthday;

    @Builder.Default
    @Size(min = 2, max = 5)
    @Column(name = "currency", nullable = false)
    private String currency = CurrencyType.EURO.toString();

    @Size(min = 5, max = 100)
    @Column(name = "email", nullable = false, unique = true) 
    private String email;

    @NotNull
    @Column(name = "mobile", unique = true)
    private String mobile;

    @Size(min = 5, max = 256)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Size(min = 3, max = 100)
    @Column(name = "username", nullable = false, unique = true)
    private String alias;

    @Builder.Default
    @Size(min = 2, max = 5)
    @Column(name = "language", nullable = false)
    private String language = LanguageType.ENGLISH.toString();

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "cookie")
    private Boolean cookie;

    @Column(name = "track")
    private Boolean track;

    @Column(name = "gdpr")
    private Boolean gdpr;

    @Builder.Default
    @Size(min = 2, max = 8)
    @Column(name = "measurement", nullable = false)
    private String measurement = MeasurementType.METRIC.toString();

    @Column(name = "notify")
    private Boolean notify;

    @JsonIgnore()
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Advert> adverts;

    @JsonIgnore()
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Article> articles;

    @JsonIgnore()
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @JsonIgnore()
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> saleReviews;
  
    @JsonIgnore()
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> purchaseReviews;

    @JsonIgnore()
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Wallet> wallets;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("assets")  
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id" , nullable = true), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true))
    @ManyToMany()
    private List<Asset> purchases = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("assets")  
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true))
    @ManyToMany()
    private List<Asset> sales = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Game> games = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("sellers")
    @ManyToMany(mappedBy = "sellers" )
    private List<Location> deliveries  = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("buyers")
    @ManyToMany(mappedBy = "buyers" )
    private List<Location> collections = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Message> messages = new HashSet<>();

    @PrePersist
    private void preInsert() {
        if (this.timeZone == null) {
            this.timeZone = "CET";
        }
        if (super.getPublishDate()== null) {
            this.setPublishDate(System.currentTimeMillis() / 1000);
        }
    }

}