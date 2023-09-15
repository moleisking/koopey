package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.koopey.api.model.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("tags")
    @JoinTable(name = "classification", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"))
    @ManyToMany()
    @ToString.Exclude
    private Set<Asset> assets = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Classification.class, mappedBy = "tag")
    @ToString.Exclude
    private List<Classification> classifications = new ArrayList<>();

}