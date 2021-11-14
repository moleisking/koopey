package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "message")
public class Message  extends BaseEntity {

    private static final long serialVersionUID = -1434147244129423817L;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @JoinTable(name = "conversation", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false))
    @ManyToMany()
    @ToString.Exclude  
    private Set<User> users = new HashSet<>();  
}