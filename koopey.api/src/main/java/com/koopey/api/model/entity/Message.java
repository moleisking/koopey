package com.koopey.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "message")
public class Message  extends BaseEntity {

    private static final long serialVersionUID = -1434147244129423817L;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "content")
    private String content;
   
    // https://www.javaguides.net/2019/06/spring-boot-angular-8-websocket-example-tutorial.html

}