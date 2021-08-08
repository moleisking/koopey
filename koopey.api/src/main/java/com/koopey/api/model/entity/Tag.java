package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper=true ,exclude = "assets")
@NoArgsConstructor
@SuperBuilder
@Table(name = "tag")
public class Tag extends BaseEntity {

    private static final long serialVersionUID = 7556090450210573431L;

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

    @Column(name = "nl")
    private String nl;

    @Column(name = "pt")
    private String pt;

    @Builder.Default
    @JsonIgnoreProperties("tags")  
    @JoinTable(name = "classification", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<Asset> assets = new HashSet<>();

    // @Override
    // public String toString() {
    //     return MoreObjects.toStringHelper(this).add("id", id).add("cn", cn).add("en", en).add("es", es).add("de", de)
    //             .add("fr", fr).add("it", it).add("pt", pt).add("zh", zh).add("publish", publishDate).toString();
    // }
}