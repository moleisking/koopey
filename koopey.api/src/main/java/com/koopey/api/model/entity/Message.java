package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

  /*  @Column(name = "user_id" , length=16 , nullable = false)
    protected UUID senderId;

    @Column(name = "user_id" , length=16 , nullable = false)
    protected UUID receiverId;*/

    @EqualsAndHashCode.Exclude    
    @JoinColumn(name = "receiver_id", nullable = false)
    @JsonIgnore   
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private User receiver; 

    @EqualsAndHashCode.Exclude    
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonIgnore   
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private User sender; 

    @Column(name = "delivered")
    private Boolean delivered;

   /* @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @JoinTable(name = "conversation", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false))
    @ManyToMany()
    @ToString.Exclude  
    private Set<User> users = new HashSet<>();  */
}