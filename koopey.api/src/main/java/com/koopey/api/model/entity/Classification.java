package com.koopey.api.model.entity;

import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode( callSuper=true)
public class Classification extends BaseEntity { 
}
