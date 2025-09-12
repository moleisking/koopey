package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.util.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@SuperBuilder
@Table(name = "game")
public class Game extends BaseEntity {

    private static final long serialVersionUID = 7523090550210783431L;

    @Column(name = "duration")
    private long duration;

    @Column(name = "black_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID blackId;

    @Builder.Default
    @Column(name = "blackScore")
    private long blackScore = 0;

    @Column(name = "blue_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID blueId;

    @Builder.Default
    @Column(name = "blueScore")
    private long blueScore = 0;

    @Column(name = "green_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID greenId;

    @Builder.Default
    @Column(name = "greenScore")
    private long greenScore = 0;

    @Column(name = "grey_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID greyId;

    @Builder.Default
    @Column(name = "greyScore")
    private long greyScore = 0;

    @Column(name = "red_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID redId;

    @Builder.Default
    @Column(name = "redScore")
    private long redScore = 0;

    @Column(name = "yellow_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID yellowId;

    @Builder.Default
    @Column(name = "yellowScore")
    private long yellowScore = 0;

    @Column(name = "white_id" , length=16 , nullable = false, columnDefinition = "VARCHAR(36)")
    protected UUID whiteId;

    @Builder.Default
    @Column(name = "whiteScore")
    private long whiteScore = 0;

    @JsonIgnore()
    @JoinColumn(name = "black_id", nullable = true, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User black;

    @JsonIgnore()
    @JoinColumn(name = "blue_id", nullable = true, unique = false, insertable = false, updatable = false , referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User blue;

    @JsonIgnore()
    @JoinColumn(name = "green_id", nullable = true, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User green;

    @JsonIgnore()
    @JoinColumn(name = "grey_id", nullable = true, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User grey;

    @JsonIgnore()
    @JoinColumn(name = "red_id", nullable = true, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User red;

    @JsonIgnore()
    @JoinColumn(name = "yellow_id", nullable = true, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User yellow;

    @JsonIgnore()
    @JoinColumn(name = "white_id", nullable = true, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User white;

}
