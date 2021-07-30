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
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@Table(name = "transaction")
public class Transaction extends BaseEntity {

  private static final long serialVersionUID = 7523090550210573431L;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "asset_id", nullable = false)
  private Asset asset;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @ManyToOne
  private User receiver;
 
  @Column(name = "value")
  private int value;

  @Column(name = "reference")
  private String reference;

  // @Override
  // public String toString() {
  //   return MoreObjects.toStringHelper(this).add("id", id).add("asset", asset.getName()).add("sender", sender.getName())
  //       .add("receiver", receiver.getName()).add("type", type).add("value", value).add("reference", reference).add("publish", publishDate)
  //       .toString();
  // }
}