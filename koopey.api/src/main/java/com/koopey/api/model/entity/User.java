package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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

    @Size(min = 2, max = 5)
    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "cookie")
    private Boolean cookie;

    @Column(name = "track")
    private Boolean track;

    @Column(name = "gdpr")
    private Boolean gdpr;

    @Column(name = "notify")
    private Boolean notify;

    @JsonIgnore()
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Advert> adverts;

    @JsonIgnore()
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @JsonIgnore()
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Asset> purchases;

    @JsonIgnore()
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Asset> sales;
  
    @JsonIgnore()
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @JsonIgnore()
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Wallet> wallets;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Game> games = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users" )
    private Set<Location> locations = new HashSet<>();

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