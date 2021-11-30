package com.koopey.api.model.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class BaseDto  implements Serializable {
    
    protected UUID id;  
    protected String name;
    protected String description;
    protected String type;
    protected Long publishDate = System.currentTimeMillis() / 1000;
}
