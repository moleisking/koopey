package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;
import com.koopey.api.model.entity.Tag;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SearchDto extends BaseDto {

    private String alias;
    private String currency;
    private String measure;
    private String period;
    private Long start;
    private Long end;
    private Long max;
    private Long min;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer radius;
    private List<Tag> tags;

}