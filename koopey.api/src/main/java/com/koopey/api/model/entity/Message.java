package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;
import com.koopey.api.model.type.LanguageType;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "message")
public class Message extends BaseEntity {

    private static final long serialVersionUID = -1434147244129423817L;

    @Builder.Default
    @Size(min = 2, max = 5)
    @Column(name = "language", nullable = false)
    private String language = LanguageType.ENGLISH.toString();

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