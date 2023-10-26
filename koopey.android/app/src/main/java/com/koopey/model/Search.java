package com.koopey.model;

import com.koopey.model.base.Base;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Search extends Base  {
    @Builder.Default
    private String period = "hour";
    @Builder.Default
    private String currency = "btc";
    private String alias ;
    @Builder.Default
    private String measure = "metric";
    @Builder.Default
    private int min = 0;
    @Builder.Default
    private int max = 5000;
    @Builder.Default
    private int radius = 10;
    private Double latitude;
    private Double longitude;
    @Builder.Default
    private Tags tags = new Tags();
    @Builder.Default
    private long start = 0;
    @Builder.Default
    private long end = 0;

}
