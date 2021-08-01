package com.koopey.api.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserAutheticateDto implements Serializable {

    private String username;
    private String password;    
}