package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.AuditEntity;
import com.koopey.api.model.type.CurrencyType;
import com.koopey.api.model.type.LanguageType;
import com.koopey.api.model.type.MeasurementType;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "user")
public class User extends AuditEntity {

    private static final long serialVersionUID = -5133446600881698403L;

    @Size(max = 131072)
    @Column(name = "avatar")
    @ToString.Exclude
    private String avatar; 

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

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "average")
    private Integer average;    

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "positive")
    private Integer positive;

    @Column(name = "negative")
    private Integer negative;

    @Builder.Default
    @Column(name = "cookie")
    private Boolean cookie  = false;

    @Builder.Default
    @Column(name = "track")
    private Boolean track = false;

    @Builder.Default
    @Column(name = "gdpr")
    private Boolean gdpr  = false;

    @Builder.Default
    @Size(min = 2, max = 8)
    @Column(name = "measurement", nullable = false)
    private String measurement = MeasurementType.METRIC.toString();

    @Builder.Default
    @Column(name = "notify")
    private Boolean notify = false;

    @Builder.Default
    @Column(name = "verify")
    private Boolean verify = false;

    @JsonIgnore()
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Wallet> wallets;

    @JsonIgnore()
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> sends;

    @JsonIgnore()
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> receives;

    @Builder.Default
    @EqualsAndHashCode.Exclude     
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "advert_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "advert_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore
    @ManyToMany()
    @ToString.Exclude
    private List<Advert> adverts = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude     
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore
    @ManyToMany()
    @ToString.Exclude
    private List<Asset> purchases = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude        
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore
    @ManyToMany()
    @ToString.Exclude
    private List<Asset> sales = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore    
    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Game> games = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore   
    @ManyToMany(mappedBy = "sellers")
    @ToString.Exclude
    private List<Location> deliveries = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @ManyToMany(mappedBy = "buyers")
    @ToString.Exclude
    private List<Location> collections = new ArrayList<>();  

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @OneToMany(mappedBy="buyer",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> buyerTransactions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @OneToMany(mappedBy="seller",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> sellerTransactions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @OneToMany(mappedBy="player",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<Competition> competitions = new ArrayList<>();

    @PrePersist
    private void preInsert() {
        if (this.timeZone == null) {
            this.timeZone = "CET";
        }
        if (super.getPublish() == null) {
            this.setPublish(System.currentTimeMillis() / 1000);
        }
    }

}