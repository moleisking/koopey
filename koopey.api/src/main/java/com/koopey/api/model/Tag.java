package com.koopey.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Entity
@Data
@EqualsAndHashCode(exclude = "assets")
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 7556090450210573431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

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

    @Builder.Default
    @JsonIgnoreProperties("tags")  
    @JoinTable(name = "classification", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<Asset> assets = new HashSet<>();

    @Builder.Default
    @Column(name = "publish_date")
    private Long publishDate = System.currentTimeMillis() / 1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("cn", cn).add("en", en).add("es", es).add("de", de)
                .add("fr", fr).add("it", it).add("pt", pt).add("zh", zh).add("publish", publishDate).toString();
    }
}