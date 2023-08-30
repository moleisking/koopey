package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.AuditDto;
import com.koopey.api.model.entity.Tag;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SearchDto extends AuditDto {

    private String alias;
    private Date start;
    private Date end;
    private Long max;
    private Long min;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer radius;
    private List<Tag> tags;

}