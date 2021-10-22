package com.koopey.api.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper=true)
public class Journey extends BaseEntity {
    
    @Column(name = "asset_id" , length=16)
    protected UUID assetId;
    
    @Column(name = "location_id" , length=16)
    protected UUID locationId;

    @Column(name = "user_id" , length=16)
    protected UUID userId;
}
