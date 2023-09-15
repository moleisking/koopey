package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

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
@EqualsAndHashCode(callSuper=true)
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "wallet")
public class Wallet extends BaseEntity {

  private static final long serialVersionUID = 7523090550210573431L;

  @Column(name = "value")
  private int value;

  @Column(name = "address")
  private String address;

  @Column(name = "currency")
  private String currency;

  @Column(name = "identifier")
  private String identifier;
 
  @EqualsAndHashCode.Exclude    
  @JoinColumn(name = "owner_id", nullable = false)
  @JsonIgnore   
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
  @ToString.Exclude
  private User owner; 

}