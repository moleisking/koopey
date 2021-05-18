package com.koopey.api.model;

import com.google.common.base.MoreObjects;
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
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
@Table(name = "wallet")
public class Wallet implements Serializable {

  private static final long serialVersionUID = 7523090550210573431L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @Column(name = "type")
  private String type;

  @Column(name = "name")
  private String name;

  @Column(name = "value")
  private int value;

  @Builder.Default
  @Column(name = "publish_date")
  private Long publishDate = System.currentTimeMillis() / 1000;

  @JoinColumn(name = "owner_id", nullable = false)
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  private User owner; 

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("owner", owner.getName()).add("type", type).add("value", value)
        .add("type", type).add("publish", publishDate).toString();
  }
}