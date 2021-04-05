package com.koopey.server.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 7523090550210573431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

   // @Column(name = "avatar")
  //  private String images = "";

    private String title;


    private String type;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;

    @Builder.Default
    @Column(name = "publish_date")
    private Long publishDate = System.currentTimeMillis() / 1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("client", client.getName())
                .add("provider", provider.getName()).add("publish", publishDate).toString();
    }
}