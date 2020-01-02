package com.koopey.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Entity
@Getter
@Setter
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 7556090450210573431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "cn")
    private String cn;

    @Column(name = "de")
    private String de;

    @Column(name = "en")
    private String en;

    @Column(name = "es")
    private String es;

    @Column(name = "fr")
    private String fr;

    @Column(name = "it")
    private String it;

    @Column(name = "pt")
    private String pt;

    @Column(name = "zh")
    private String zh;

    @ManyToMany
    private Set<Asset> assets;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("cn", cn).add("en", en).add("es", es).add("de", de)
                .add("fr", fr).add("it", it).add("pt", pt).add("zh", zh).toString();
    }
}