package com.koopey.api.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode( callSuper=true)
public class Venue extends BaseEntity{
      
    @Column(name = "user_id" , length=16)
    protected UUID userId;
    
    @Column(name = "location_id" , length=16)
    protected UUID locationId;
}
