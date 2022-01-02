package com.koopey.api.model.dto.base;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class BaseDto  implements Serializable {
    
    protected UUID id;  

    protected Long publishDate = System.currentTimeMillis() / 1000;
}
