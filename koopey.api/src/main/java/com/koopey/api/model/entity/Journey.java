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
    
    @Column(name = "driver_id" , length=16)
    protected UUID driverId;
    
    @Column(name = "location_id" , length=16)
    protected UUID locationId;  

    @Column(name = "passanger_id" , length=16)
    protected UUID passangerId;

    @Column(name = "vehicle_id" , length=16)
    protected UUID vehicleId;
}
