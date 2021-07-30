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

@Data
@Entity
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "review")
public class Review extends BaseEntity {

  private static final long serialVersionUID = 7523090550210573431L;
 
  @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "asset_id", nullable = false)
  private Asset asset;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "client_id", nullable = false)
  private User client;

  @Column(name = "value")
  private int value;

  @Column(name = "content")
  private String content;

  // @Override
  // public String toString() {
  //   return MoreObjects.toStringHelper(this).add("id", id).add("client", client.getName()).add("asset", asset.getName())
  //       .add("client", client).add("type", type).add("value", value).add("content", content)
  //       .add("publish", publishDate).toString();
  // }
}