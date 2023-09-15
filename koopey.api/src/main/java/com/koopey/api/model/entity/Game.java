package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
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

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @JoinTable(name = "competition", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @ManyToMany()
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore  
    @OneToMany(mappedBy="game",cascade=CascadeType.ALL)
    @ToString.Exclude
    private List<Competition> competitions = new ArrayList<>();

}
