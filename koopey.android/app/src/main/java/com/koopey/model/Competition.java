package com.koopey.model;

import com.koopey.model.base.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Competition extends Base {
    String gameId;
    String userId;
}
