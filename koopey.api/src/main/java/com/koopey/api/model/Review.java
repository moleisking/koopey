package com.koopey.api.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Data
@Table(name = "review")
public class Review implements Serializable {

  private static final long serialVersionUID = 7523090550210573431L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "asset_id", nullable = false)
  private Asset asset;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "client_id", nullable = false)
  private User client;

  @Column(name = "type")
  private String type;

  @Column(name = "value")
  private int value;

  @Column(name = "content")
  private String content;

  @Builder.Default
  @Column(name = "publish_date")
    private Long publishDate = System.currentTimeMillis() / 1000;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("client", client.getName()).add("asset", asset.getName())
        .add("client", client).add("type", type).add("value", value).add("content", content)
        .add("publish", publishDate).toString();
  }
}