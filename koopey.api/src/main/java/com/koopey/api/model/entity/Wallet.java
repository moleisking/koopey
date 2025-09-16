package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@SuperBuilder
@Table(name = "wallet")
public class Wallet extends BaseEntity {

  private static final long serialVersionUID = 7523090550210573431L;

  @Column(name = "owner_id", length = 36, nullable = false, unique = false, columnDefinition = "VARCHAR(36)")
  @JdbcTypeCode(Types.VARCHAR)
  protected UUID ownerId;

  @Column(name = "value")
  private BigDecimal value;

  @Column(name = "address")
  private String address;

  @Column(name = "currency")
  private String currency;

  @Column(name = "identifier")
  private String identifier;

  // @EqualsAndHashCode.Exclude
  @JoinColumn(name = "owner_id", nullable = false, unique = false, insertable = false, updatable = false, referencedColumnName = "id")
  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, targetEntity = User.class)
  @ToString.Exclude
  private User owner;

}