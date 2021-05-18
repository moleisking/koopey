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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
@Table(name = "transaction")
public class Transaction implements Serializable {

  private static final long serialVersionUID = 7523090550210573431L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "asset_id", nullable = false)
  private Asset asset;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @ManyToOne
  private User receiver;

  @Column(name = "type")
  private String type;

  @Column(name = "value")
  private int value;

  @Column(name = "reference")
  private String reference;

  @Builder.Default
  @Column(name = "publish_date")
  private Long publishDate = System.currentTimeMillis() / 1000;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("asset", asset.getName()).add("sender", sender.getName())
        .add("receiver", receiver.getName()).add("type", type).add("value", value).add("reference", reference).add("publish", publishDate)
        .toString();
  }
}