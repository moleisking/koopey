package com.koopey.api.model;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "wallet")
public class Wallet implements Serializable {

  private static final long serialVersionUID = 7523090550210573431L;

  @Column(name = "value")
  private int value;
 
  @JoinColumn(name = "owner_id", nullable = false)
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  private User owner; 

  // @Override
  // public String toString() {
  //   return MoreObjects.toStringHelper(this).add("id", id).add("owner", owner.getName()).add("type", type).add("value", value)
  //       .add("type", type).add("publish", publishDate).toString();
  // }
}