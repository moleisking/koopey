package com.koopey.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@Table(name = "review")
public class Review implements Serializable {

  private static final long serialVersionUID = 7523090550210573431L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "asset_id")
  private String assetId;

  @Column(name = "sender")
  private String sender;

  @Column(name = "receiver")
  private String receiver;

  @Column(name = "type")
  private String type;

  @Column(name = "value")
  private int value;

  @Column(name = "text")
  private String text;

  @Column(name = "timestamp")
  private long timestamp = System.currentTimeMillis() / 1000;;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("userId", userId).add("assetId", assetId)
        .add("sender", sender).add("receiver", receiver).add("type", type).add("value", value).add("type", type)
        .add("timestamp", timestamp).toString();
  }
}