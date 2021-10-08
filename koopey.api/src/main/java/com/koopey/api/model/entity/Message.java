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

    @Column(name = "arrive")
    private Boolean delivered;

    @Column(name = "sent")
    private Boolean sent;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "content")
    private String content;

}