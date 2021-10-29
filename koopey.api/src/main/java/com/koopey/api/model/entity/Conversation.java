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
public class Conversation extends BaseEntity {
    
    @Column(name = "user_id" , length=16 , nullable = false)
    protected UUID userId;
    
    @Column(name = "message_id" , length=16 , nullable = false)
    protected UUID messageId;

    @Column(name = "received")
    private Boolean received;

    @Column(name = "sent")
    private Boolean sent;
}
