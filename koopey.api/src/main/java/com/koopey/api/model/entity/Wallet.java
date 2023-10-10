package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "wallet")
public class Wallet extends BaseEntity {

  private static final long serialVersionUID = 7523090550210573431L;

  @Column(name = "owner_id", length = 16, nullable = false, unique = false)
  protected UUID ownerId;

  @Column(name = "value")
  private int value;

  @Column(name = "address")
  private String address;

  @Column(name = "currency")
  private String currency;

  @Column(name = "identifier")
  private String identifier;

  // @EqualsAndHashCode.Exclude
  @JoinColumn(name = "owner_id", nullable = false, unique = false, insertable = false, updatable = false)
  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, targetEntity = User.class)
  @ToString.Exclude
  private User owner;

}