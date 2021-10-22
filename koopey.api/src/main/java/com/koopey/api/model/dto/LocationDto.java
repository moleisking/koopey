package com.koopey.api.model.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class LocationDto implements Serializable{
        
    public UUID id = UUID.randomUUID();  
    public String name ;
    public String description ;  
    public String latitude ;
    public String longitude ;

}