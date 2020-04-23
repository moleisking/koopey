package com.koopey.server.model;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


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

    @Size(min = 3, max = 100)
    @Column(name = "alias")
    private String alias;

    @Size(min = 3, max = 50)
    @Column(name = "name")    
    private String name;

    @Size(min = 3, max = 100)
    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Size(min = 5, max = 256)
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

    private String token;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("alias", alias).add("birthday", birthday)
                .add("description", description).add("name", name).add("timestamp", timestamp).add("timeZone", timeZone)
                .toString();
    }

}