package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewDto extends AuditDto{
    
    public String assetId;
    public String buyerId;
    public String sellerId;   
  
}
