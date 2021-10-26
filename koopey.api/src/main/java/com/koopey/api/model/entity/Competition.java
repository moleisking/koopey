package com.koopey.api.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode( callSuper=true)
public class Competition extends BaseEntity {
  
    @Column(name = "game_id" , length=16 , nullable = false)
    protected UUID gameId;
    
    @Column(name = "user_id" , length=16 , nullable = false)
    protected UUID userId;
}
