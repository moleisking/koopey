package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(exclude = "users", callSuper=true)
@Table(name = "game")
public class Game extends BaseEntity {

    private static final long serialVersionUID = 7523090550210783431L;

    @Column(name = "duration")
    private long duration;

    @Column(name = "score")
    private long score;

    @Builder.Default
    @JsonIgnoreProperties("games")
    @JoinTable(name = "competition", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @ManyToMany()
    private Set<User> users = new HashSet<>();
 
}
