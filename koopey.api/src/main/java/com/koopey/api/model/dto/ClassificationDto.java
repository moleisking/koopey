package com.koopey.api.model.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class ClassificationDto implements Serializable{
    
    public UUID id = UUID.randomUUID();
    public String asset_id ;
    public String tag_id ;

}
