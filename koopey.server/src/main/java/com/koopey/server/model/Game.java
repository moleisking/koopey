package com.koopey.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Entity
@Data
@EqualsAndHashCode(exclude = "users")
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 7523090550210783431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "duration")
    private long duration;

    /*
     * public defeats: Array<boolean> = new Array<boolean>( false, false, false,
     * false ); //B,G,R,Y public moves: Array<string> = new Array<string>();
     * 
     * public token: PlayerType = PlayerType.Blue; // First move is always blue
     */

    @Builder.Default
    @JsonIgnoreProperties("games")
    @JoinTable(name = "competition", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @Column(name = "publish_date")
    private Long publishDate = System.currentTimeMillis() / 1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("type", type).add("duration", duration)
                .add("publish", publishDate).toString();
    }

}
