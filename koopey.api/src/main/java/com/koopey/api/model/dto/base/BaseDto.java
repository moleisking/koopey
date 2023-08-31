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

    @Builder.Default
    protected Long publishDate = System.currentTimeMillis() / 1000;
}
