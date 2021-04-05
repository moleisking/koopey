package com.koopey.server.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = -1434147244129423817L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id ;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "content")
    private String content;

    @Builder.Default
    @Column(name = "timestamp")
    private Long timestamp = System.currentTimeMillis() / 1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("sender", sender).add("content", content)
                .add("receiver", receiver).add("timestamp", timestamp).toString();
    }

    // https://www.javaguides.net/2019/06/spring-boot-angular-8-websocket-example-tutorial.html

}