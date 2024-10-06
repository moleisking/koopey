package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.util.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "game")
public class Game extends BaseEntity {

    private static final long serialVersionUID = 7523090550210783431L;

    @Column(name = "duration")
    private long duration;

    @Builder.Default
    @Column(name = "score")
    private long score = 0;

    @Column(name = "black_id" , length=16 , nullable = false)
    protected UUID blackId;

    @Column(name = "blue_id" , length=16 , nullable = false)
    protected UUID blueId;

    @Column(name = "green_id" , length=16 , nullable = false)
    protected UUID greenId;

    @Column(name = "grey_id" , length=16 , nullable = false)
    protected UUID greyId;

    @Column(name = "red_id" , length=16 , nullable = false)
    protected UUID redId;

    @Column(name = "yellow_id" , length=16 , nullable = false)
    protected UUID yellowId;

    @Column(name = "white_id" , length=16 , nullable = false)
    protected UUID whiteId;

    @JsonIgnore()
    @JoinColumn(name = "black_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User black;

    @JsonIgnore()
    @JoinColumn(name = "blue_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User blue;

    @JsonIgnore()
    @JoinColumn(name = "green_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User green;

    @JsonIgnore()
    @JoinColumn(name = "grey_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User grey;

    @JsonIgnore()
    @JoinColumn(name = "red_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User red;

    @JsonIgnore()
    @JoinColumn(name = "yellow_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User yellow;

    @JsonIgnore()
    @JoinColumn(name = "white_id", nullable = true, unique = true, insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    private User white;

}
