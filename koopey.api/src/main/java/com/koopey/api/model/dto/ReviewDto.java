package com.koopey.api.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewDto extends BaseDto{
    
    public String assetId;
    public String buyerId;
    public String sellerId;   
  
}
