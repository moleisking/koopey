package com.koopey.api.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class LocationDto implements Serializable{
        
    public UUID id = UUID.randomUUID();  
    public String name ;
    public String description ;  
    public BigDecimal altitude ;
    public BigDecimal distance ;
    public BigDecimal latitude ;
    public BigDecimal longitude ; 
}