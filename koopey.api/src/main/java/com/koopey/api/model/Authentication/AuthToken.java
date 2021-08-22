package com.koopey.api.model.authentication;

import java.util.UUID;

import lombok.Data;

@Data
public class AuthToken {

    private String token;
    private UUID id;

    public AuthToken(){}

    public AuthToken( UUID id,String token){
        this.id =id;
        this.token = token;       
    }

}