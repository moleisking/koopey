package com.koopey.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -5133446600881698403L;

    
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @GeneratedValue(generator = "uuid") // system-uuid
    @GenericGenerator(name = "uuid", strategy = "uuid2") // system-uuid
    @Column(name = "id")
    private String id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "timestamp")
    private Long timestamp = System.currentTimeMillis() / 1000;

    @ManyToMany
    private Set<Asset> assets;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("alias", alias).add("birthday", birthday)
                .add("description", description).add("name", name).add("timestamp", timestamp).add("timeZone", timeZone)
                .toString();
    }

}