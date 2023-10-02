package com.koopey.api.model.dto.base;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public class BaseDto implements Serializable {
    protected UUID id;
    protected String name;
    protected String description;
    protected String type;
    @Builder.Default
    protected String timeZone = "UTC/GMT";
    @Builder.Default
    protected Long createTimeStamp = System.currentTimeMillis() / 1000;
    @Builder.Default
    protected Long readTimeStamp = 0L;
    @Builder.Default
    protected Long updateTimeStamp = 0L;
    @Builder.Default
    protected Long deleteTimeStamp = 0L;
}
