package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.dto.MessageDto;
import com.koopey.api.model.entity.base.AuditEntity;
import com.koopey.api.model.entity.base.BaseEntity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Slf4j
@Table(name = "message")
public class Message extends AuditEntity {

    private static final long serialVersionUID = -1434147244129423817L;

    @Column(name = "delivered")
    private Boolean delivered;

    @Column(name = "receiver_id", length = 16, nullable = false)
    protected UUID receiverId;

    @Column(name = "sender_id", length = 16, nullable = false)
    protected UUID senderId;

    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "receiver_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private User receiver;

    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "sender_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private User sender;  

}