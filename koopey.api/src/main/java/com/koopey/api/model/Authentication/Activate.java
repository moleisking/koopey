package com.koopey.api.model.authentication;

import java.io.Serializable;

import lombok.Data;

@Data
public class Activate implements Serializable {
    private String guid;
}
