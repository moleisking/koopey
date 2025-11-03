package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;
import com.koopey.api.model.type.AuthenticationType;
import com.koopey.api.model.type.CurrencyType;
import com.koopey.api.model.type.LanguageType;
import com.koopey.api.model.type.MeasureType;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@SuperBuilder
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = -5133446600881698403L;

    @Size(max = 131072)
    @Column(name = "avatar")
    @ToString.Exclude
    private String avatar;

    @Builder.Default
    @Size(min = 2, max = 5)
    @Column(name = "currency", nullable = false)
    private String currency = CurrencyType.EURO.toString();

    @Column(name = "device", unique = true)
    private String device;

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

    @Size(min = 3, max = 100)
    @Column(name = "ip", nullable = true, unique = false)
    private String ip;

    @Builder.Default
    @Size(min = 2, max = 5)
    @Column(name = "language", nullable = false)
    private String language = LanguageType.ENGLISH.toString();

    @Builder.Default
    @Size(min = 2, max = 8)
    @Column(name = "measure", nullable = false)
    private String measure = MeasureType.METRIC.toString();

    @Builder.Default
    @Column(name = "guid" , length=36, unique = true, nullable = true, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID guid = UUID.randomUUID();

    @Column(name = "birthday")
    private Long birthday;

    @Column(name = "average")
    private Integer average;

    @Column(name = "altitude")
    private BigDecimal altitude;

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
    private Boolean cookie = false;

    @Builder.Default
    @Column(name = "track")
    private Boolean track = false;

    @Builder.Default
    @Column(name = "gdpr")
    private Boolean gdpr = false;

    @Builder.Default
    @Column(name = "term")
    private Boolean term = false;

    @Builder.Default
    @Column(name = "notifyByEmail")
    private Boolean notifyByEmail = false;

    @Builder.Default
    @Column(name = "notifyByDevice")
    private Boolean notifyByDevice = false;

    @Enumerated(EnumType.STRING)
    private AuthenticationType authenticationType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Objects.requireNonNullElse(authenticationType, AuthenticationType.USER).toString()));
    }

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

    /*@Builder.Default
    @EqualsAndHashCode.Exclude
    @JoinTable(name = "transaction", joinColumns = @JoinColumn(name = "advert_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "advert_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore
    @ManyToMany()
    @ToString.Exclude
    private List<Advert> adverts = new ArrayList<>();*/

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

   /* @Builder.Default
    @EqualsAndHashCode.Exclude
    @JoinTable(name = "game", joinColumns = @JoinColumn(name = "white_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "black_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false))
    @JsonIgnore
    @ManyToMany()
    @ToString.Exclude
    private List<Game> games = new ArrayList<>();*/

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
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> buyerTransactions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> sellerTransactions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "black", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> blackGames = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "blue", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> blueGames = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "green", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> greenGames = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "grey", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> greyGames = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "red", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> redGames = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "yellow", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> yellowGames = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "white", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> whiteGames = new ArrayList<>();

    @PrePersist
    private void preInsert() {
        if (super.getCreateTimeStamp() == 0) {
            this.setCreateTimeStamp(System.currentTimeMillis() / 1000);
        }
    }

    @Override
    public String getUsername() {
        return alias;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}