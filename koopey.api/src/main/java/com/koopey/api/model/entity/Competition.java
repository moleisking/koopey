package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode( callSuper=true)
public class Competition extends BaseEntity {
  
    @Column(name = "game_id" , length=16 , nullable = false)
    protected UUID gameId;
    
    @Column(name = "user_id" , length=16 , nullable = false)
    protected UUID userId;

    @JsonIgnore()
    @JoinColumn(name = "game_id", nullable = false, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL , optional = true)
    private Game game;

    @JsonIgnore()
    @JoinColumn(name = "user_id", nullable = false, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL , optional = false)
    private User player;
}
