package com.koopey.api.model.authentication;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Activate implements Serializable {
    protected String guid;
}
