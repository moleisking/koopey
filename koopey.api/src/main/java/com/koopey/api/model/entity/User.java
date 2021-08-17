package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(callSuper=true, exclude = "games")
@NoArgsConstructor
@SuperBuilder
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -5133446600881698403L;

    @Size( max = 131072)
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday")
    private Long birthday;

    @Size(min = 3, max = 100)
    @Column(name = "email", nullable = false) 
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
    private String username;

    @Column(name = "time_zone")
    private String timeZone;

    @JsonIgnore()
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Advert> advert;

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
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Location> locations;

    @JsonIgnore()
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> messages;

    @JsonIgnore()
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @JsonIgnore()
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Wallet> wallets;

    @Builder.Default
    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Game> games = new HashSet<>();

    private String token;

    private String getAlias() {
        return username;
    }

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