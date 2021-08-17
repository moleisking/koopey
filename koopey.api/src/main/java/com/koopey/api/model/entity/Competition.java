package com.koopey.api.model.entity;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode( callSuper=true)
public class Competition extends BaseEntity {
  
}
