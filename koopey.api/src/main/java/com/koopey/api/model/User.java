package com.koopey.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Entity
@Data
@EqualsAndHashCode(exclude = "games")
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -5133446600881698403L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday")
    private Date birthday;

    @Size(min = 3, max = 100)
    @Column(name = "email", nullable = false) // user search should allow nullable = true
    private String email;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "mobile", unique = true) // user search should allow nullable = true
    private String mobile;

    @Size(min = 3, max = 50)
    @Column(name = "name")
    private String name;

    @Size(min = 5, max = 256)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Size(min = 3, max = 100)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "publish_date")
    private Long publishDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Advert> advert;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Asset> purchases;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Asset> sales;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> messages;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews;

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
        if (this.publishDate == null) {
            this.publishDate = System.currentTimeMillis() / 1000;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("birthday", birthday)
                .add("description", description).add("name", name).add("publish", publishDate).add("timeZone", timeZone)
                .toString();
    }

}